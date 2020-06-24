package rs.grgur.jovan.optika.api;

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
import org.springframework.web.servlet.ModelAndView;
import rs.grgur.jovan.optika.model.Account;
import rs.grgur.jovan.optika.model.Business;
import rs.grgur.jovan.optika.model.Card;
import rs.grgur.jovan.optika.model.Order;
import rs.grgur.jovan.optika.model.PaymentForm;
import rs.grgur.jovan.optika.repository.BusinessRepository;
import rs.grgur.jovan.optika.repository.CardRepository;
import rs.grgur.jovan.optika.repository.OrderRepository;

@Controller
@RequestMapping(value = "/payment")
public class PaymentController {

    @Autowired
    private BusinessRepository businessRepository;
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private CardRepository cardRepository;

    @PostMapping(value = {"/init"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @CrossOrigin
    public ResponseEntity paymentInit(@RequestBody HashMap<String, String> request) {
        String merchantId = request.get("MERCAHNT_ID");
        String merchantPassword = request.get("MERCHANT_PASSWORD");
        double amount = Double.parseDouble(request.get("AMOUNT"));
        String merchantOrderId = request.get("MERCAHNT_ORDER_ID");
        String successUrl = request.get("SUCCESS_URL");
        String failedUrl = request.get("FAILED_URL");
        String errorUrl = request.get("ERROR_URL");
        Date merchantTimestamp = null;
        try {
            merchantTimestamp = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(request.get("MERCHANT_TIMESTAMP"));
        } catch (Exception ex) {
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
        }
        
        Business business = businessRepository.findById(merchantId).get();
        
        if (!business.getMerchantPassword().equals(merchantPassword)) {
            return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
        }
        
        Order order = new Order();
        order.setBusiness(business);
        order.setAmount(amount);
        order.setTimestamp(merchantTimestamp);
        order.setSuccessUrl(successUrl);
        order.setFailedUrl(failedUrl);
        order.setErrorUrl(errorUrl);
        order.setPaid(false);
        order = orderRepository.save(order);
        
        order.setPaymentUrl("http://localhost:8091/payment/" + order.getOrderId());
        order = orderRepository.save(order);
        
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
    
    @PostMapping(value = "/pcc", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @CrossOrigin
    public ResponseEntity postPaymentFromPCC(@RequestBody HashMap<String, String> request) {
        double amount = Double.parseDouble(request.get("amount"));
        
        Optional<Card> optionalCard = cardRepository.findById(request.get("pan"));
        if (optionalCard.isPresent()) {
            Card card = optionalCard.get();
            if (!card.getSecurityCode().equals(request.get("security_code")) ||
                !card.getCardholderName().toLowerCase().equals(request.get("cardholder_name").toLowerCase()) ||
                !new SimpleDateFormat("MM/yy").format(card.getExpiryDate()).equals(request.get("expiry_date"))) {
                return new ResponseEntity("", HttpStatus.NOT_FOUND);
            }
            
            Account account = card.getAccount();
            if (account.getBalance() - account.getReserved() < amount) {
                return new ResponseEntity("", HttpStatus.NOT_ACCEPTABLE);
            }
            
            account.setReserved(amount);
            account.setBalance(account.getBalance() - amount);
        } else {
            return new ResponseEntity("", HttpStatus.NOT_FOUND);
        }
        
        Map<String, String> map = new HashMap<>();
        map.put("ACQUIRER_ORDER_ID", request.get("ACQUIRER_ORDER_ID"));
        map.put("ACQUIRER_TIMESTAMP", request.get("ACQUIRER_TIMESTAMP"));
        map.put("ISSUER_ORDER_ID", UUID.randomUUID().toString());
        map.put("ISSUER_TIMESTAMP", String.valueOf(new Date()));
        
        return new ResponseEntity(map, HttpStatus.OK);
    }
    
    @GetMapping(value = "/{id}/data", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @CrossOrigin
    public ResponseEntity getPaymentData(@PathVariable("id") long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (!optionalOrder.isPresent()) {
            return new ResponseEntity("", HttpStatus.NOT_FOUND);
        }
        Order order = optionalOrder.get();
        
        Map<String, String> map = new HashMap<>();
        map.put("MERCHANT_ORDER_ID", String.valueOf(order.getMerchantOrderId()));
        map.put("ACQUIRER_ORDER_ID", String.valueOf(id));
        map.put("ACQUIRER_TIMESTAMP", String.valueOf(order.getAcquirerTimestamp()));
        map.put("PAYMENT_ID", String.valueOf(id));
        map.put("PAID", order.getPaid() ? "1" : "0");
        
        return new ResponseEntity(map, HttpStatus.OK);
    }
    
    @GetMapping(value = "/{id}")
    public ModelAndView showPayment(@PathVariable("id") long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (!optionalOrder.isPresent()) {
            return new ModelAndView("error", HttpStatus.NOT_FOUND);
        }
        Order order = optionalOrder.get();
        
        ModelAndView modelAndView = new ModelAndView("payment");
        modelAndView.addObject("amount", order.getAmount());
        return modelAndView;
    }
    
    @PostMapping("/{id}")
    public ModelAndView postPayment(@ModelAttribute() PaymentForm request, @PathVariable("id") long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (!optionalOrder.isPresent()) {
            return new ModelAndView("error", HttpStatus.NOT_FOUND);
        }
        Order order = optionalOrder.get();
        
        Optional<Card> optionalCard = cardRepository.findById(request.getPan());
        if (optionalCard.isPresent()) {
            Card card = optionalCard.get();
            if (!card.getSecurityCode().equals(request.getSecurityCode()) ||
                !card.getCardholderName().toLowerCase().equals(request.getCardholderName().toLowerCase()) ||
                !new SimpleDateFormat("MM/yy").format(card.getExpiryDate()).equals(request.getExpiryDate())) {
                return new ModelAndView("redirect:" + order.getFailedUrl());
            }
            
            Account account = card.getAccount();
            if (account.getBalance() - account.getReserved() < order.getAmount()) {
                return new ModelAndView("redirect:" + order.getFailedUrl());
            }
            
            account.setReserved(order.getAmount());
            account.setBalance(account.getBalance() - order.getAmount());
            
            order.setPaid(true);
            order.setAcquirerTimestamp(new Date());
            orderRepository.save(order);
        } else {
            try {
                order.setAcquirerTimestamp(new Date());
                
                JsonObject json = new JsonObject();
                json.addProperty("amount", String.valueOf(order.getAmount()));
                json.addProperty("pan", request.getPan());
                json.addProperty("security_code", request.getSecurityCode());
                json.addProperty("cardholder_name", request.getCardholderName());
                json.addProperty("expiry_date", request.getExpiryDate());
                json.addProperty("MERCHANT_ORDER_ID", String.valueOf(order.getMerchantOrderId()));
                json.addProperty("ACQUIRER_ORDER_ID", String.valueOf(order.getOrderId()));
                json.addProperty("ACQUIRER_TIMESTAMP", String.valueOf(order.getAcquirerTimestamp()));
                json.addProperty("PAYMENT_ID", String.valueOf(order.getOrderId()));
                
                OkHttpClient httpClient = new OkHttpClient();
                
                okhttp3.MediaType JSON = okhttp3.MediaType.parse("application/json; charset=utf-8");
                okhttp3.RequestBody postRequestBody = okhttp3.RequestBody.create(json.toString(), JSON);

                Request postRequest = new Request.Builder()
                        .url("http://localhost:8090/payment")
                        .post(postRequestBody)
                        .build();
                
                try (Response response = httpClient.newCall(postRequest).execute()) {
                    if (!response.isSuccessful()) {
                        return new ModelAndView("redirect:" + order.getFailedUrl());
                    }
                    order.setPaid(true);
                    orderRepository.save(order);
                }
            } catch (Exception ex) {
                return new ModelAndView("redirect:" + order.getFailedUrl());
            }
        }
        
        return new ModelAndView("redirect:" + order.getSuccessUrl());
    }

    public BusinessRepository getBusinessRepository() {
        return businessRepository;
    }

    public void setBusinessRepository(BusinessRepository businessRepository) {
        this.businessRepository = businessRepository;
    }

    public OrderRepository getAdresaorderRepository() {
        return orderRepository;
    }

    public void setAdresaorderRepository(OrderRepository adresaorderRepository) {
        this.orderRepository = adresaorderRepository;
    }

    public OrderRepository getOrderRepository() {
        return orderRepository;
    }

    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public CardRepository getCardRepository() {
        return cardRepository;
    }

    public void setCardRepository(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }
}
