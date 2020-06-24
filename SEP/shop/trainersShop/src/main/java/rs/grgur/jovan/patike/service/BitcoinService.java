package rs.grgur.jovan.patike.service;

import org.apache.http.impl.client.HttpClients;
import org.brunocvcunha.coinpayments.CoinPayments;
import org.brunocvcunha.coinpayments.model.BasicInfoResponse;
import org.brunocvcunha.coinpayments.model.CreateTransactionResponse;
import org.brunocvcunha.coinpayments.model.ResponseWrapper;
import org.brunocvcunha.coinpayments.requests.CoinPaymentsBasicAccountInfoRequest;
import org.brunocvcunha.coinpayments.requests.CoinPaymentsCreateTransactionRequest;
import org.springframework.stereotype.Service;

@Service
public class BitcoinService {

    private String publicKey = "5cc43d17817c52e9e90554b28e863e5ee5505cfc136ead70b3137500f30c7224";
    private String privateKey = "6a0f26F0F3d4454Dca9b47E6959BE0c1c52e61a7BE893db87e9b9a8fd69E89aA";

    public String createPayment(long orderId,
            double amount,
            String successUrl,
            String failedUrl) {
        String result = new String();

        try {
            CoinPayments api = CoinPayments.builder()
                    .publicKey(publicKey)
                    .privateKey(privateKey)
                    .client(HttpClients.createDefault()).build();

            ResponseWrapper<BasicInfoResponse> accountInfo = api.sendRequest(new CoinPaymentsBasicAccountInfoRequest());
            
            ResponseWrapper<CreateTransactionResponse> txResponse = api.sendRequest(CoinPaymentsCreateTransactionRequest.builder()
                    .amount(amount * 0.0093)
                    .currencyPrice("USD")
                    .currencyTransfer("LTCT")	// BTC
                    .callbackUrl(successUrl)
                    .custom("Order: #" + String.valueOf(orderId))
                    .build());
            result = txResponse.getResult().getStatusUrl();
        } catch (Exception ex) {
        }

        return result;
    }
}
