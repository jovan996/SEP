package rs.grgur.jovan.patike.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.text.SimpleDateFormat;
import java.util.Date;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

@Service
public class PayCardService {

    private String merchantId = "NS2100508PAT";
    private String merchantPassword = "merchpassword123";

    public String createPayment(long orderId,
            double amount,
            String successUrl,
            String failedUrl) {
        String result = new String();

        try {
            JsonObject json = new JsonObject();
            json.addProperty("MERCAHNT_ID", merchantId);
            json.addProperty("MERCHANT_PASSWORD", merchantPassword);
            json.addProperty("AMOUNT", String.valueOf(amount));
            json.addProperty("MERCAHNT_ORDER_ID", String.valueOf(orderId));
            json.addProperty("SUCCESS_URL", successUrl);
            json.addProperty("FAILED_URL", failedUrl);
            json.addProperty("ERROR_URL", failedUrl);
            json.addProperty("MERCHANT_TIMESTAMP", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));

            OkHttpClient httpClient = new OkHttpClient();

            okhttp3.MediaType JSON = okhttp3.MediaType.parse("application/json");
            okhttp3.RequestBody postRequestBody = okhttp3.RequestBody.create(json.toString(), JSON);

            Request postRequest = new Request.Builder()
                    .url("http://localhost:8091/payment/init")
                    .post(postRequestBody)
                    .build();

            try (Response response = httpClient.newCall(postRequest).execute()) {
                if (response.isSuccessful()) {
                    JsonParser parser = new JsonParser();
                    JsonObject jsonResponse = parser.parse(response.body().string()).getAsJsonObject();
                    result = jsonResponse.get("paymentUrl").getAsString();
                }
            }
        } catch (Exception ex) {
        }

        return result;
    }
}
