package rs.grgur.jovan.patike.api;

import com.google.gson.JsonObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import rs.grgur.jovan.patike.model.Card;
import rs.grgur.jovan.patike.repository.CardRepository;

@RestController
@CrossOrigin
@RequestMapping(value = "/payment")
public class PaymentController {

    @Autowired
    private CardRepository cardRepository;

    @PostMapping(value = "")
    @ResponseBody
    public ResponseEntity postPayment(@RequestBody HashMap<String, String> request) {
        Optional<Card> optionalCard = cardRepository.findById(request.get("pan"));
        if (optionalCard.isPresent()) {
            Card card = optionalCard.get();

            try {
                JsonObject json = new JsonObject();

                for (Map.Entry<String, String> entry : request.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();

                    json.addProperty(key, value);
                }
                OkHttpClient httpClient = new OkHttpClient();

                okhttp3.MediaType JSON = okhttp3.MediaType.parse("application/json; charset=utf-8");
                okhttp3.RequestBody postRequestBody = okhttp3.RequestBody.create(json.toString(), JSON);

                Request postRequest = new Request.Builder()
                        .url(card.getBankUrl())
                        .post(postRequestBody)
                        .build();

                try (Response response = httpClient.newCall(postRequest).execute()) {
                    if (response.isSuccessful()) {
                        return new ResponseEntity<>(response.body().string(), HttpStatus.OK);
                    }
                }
            } catch (Exception ex) {
            }
        }

        return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
    }

    public CardRepository getCardRepository() {
        return cardRepository;
    }

    public void setCardRepository(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }
}
