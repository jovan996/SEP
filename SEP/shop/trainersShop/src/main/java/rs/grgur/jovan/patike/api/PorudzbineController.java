package rs.grgur.jovan.patike.api;

import com.google.gson.JsonObject;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rs.grgur.jovan.patike.model.KorisnikDetalji;
import rs.grgur.jovan.patike.model.Porudzbina;
import rs.grgur.jovan.patike.model.PorudzbineArtikli;
import rs.grgur.jovan.patike.model.StavkaKorpe;
import rs.grgur.jovan.patike.repository.AdresaRepository;
import rs.grgur.jovan.patike.repository.NacinPlacanjaRepository;
import rs.grgur.jovan.patike.repository.PorudzbinaArtiklRepository;
import rs.grgur.jovan.patike.repository.PorudzbinaRepository;
import rs.grgur.jovan.patike.repository.StavkaKorpeRepository;
import rs.grgur.jovan.patike.service.BitcoinService;
import rs.grgur.jovan.patike.service.KorisnikDetaljiService;
import rs.grgur.jovan.patike.service.PayCardService;
import rs.grgur.jovan.patike.service.PaypalService;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/porudzbine", produces = {MediaType.APPLICATION_JSON_VALUE})
public class PorudzbineController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KorisnikDetaljiService korisnikDetaljiService;

    @Autowired
    private PorudzbinaRepository repository;

    @Autowired
    private AdresaRepository adresaRepository;

    @Autowired
    private NacinPlacanjaRepository nacinPlacanjaRepository;

    @Autowired
    private PorudzbinaArtiklRepository porudzbinaArtiklRepository;

    @Autowired
    private StavkaKorpeRepository stavkaKorpeRepository;

    @Autowired
    private PaypalService paymentService;
    
    @Autowired
    private PayCardService payCardService;
    
    @Autowired
    private BitcoinService bitcoinService;

    @GetMapping("/user/admin")
    public ResponseEntity<List<Porudzbina>> preuzmiSvePorudzbine() {
        return new ResponseEntity<List<Porudzbina>>(repository.findAll(Sort.by(Sort.Direction.DESC, "id")), HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<Set<Porudzbina>> preuzmiPorudzbine(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        KorisnikDetalji korisnikDetalji
                = korisnikDetaljiService.findByToken(StringUtils.removeStart(token, "Bearer").trim()).get();
        return new ResponseEntity<Set<Porudzbina>>(korisnikDetalji.getPorudzbine(), HttpStatus.OK);
    }

    @GetMapping("/user/admin/{id}")
    public ResponseEntity<Porudzbina> preuzmiPorudzbinu(@PathVariable("id") long id) {
        return new ResponseEntity<Porudzbina>(repository.findById(id).get(), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Porudzbina> preuzmiPorudzbinu(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable("id") long id) {
        KorisnikDetalji korisnikDetalji
                = korisnikDetaljiService.findByToken(StringUtils.removeStart(token, "Bearer").trim()).get();
        Porudzbina porudzbina = repository.findById(id).get();
        if (korisnikDetalji.getId() == porudzbina.getKorisnik().getId()) {
            return new ResponseEntity<Porudzbina>(porudzbina, HttpStatus.OK);
        }
        return new ResponseEntity<Porudzbina>(new Porudzbina(), HttpStatus.OK);
    }

    @PostMapping("/user")
    public ResponseEntity<Porudzbina> dodajPorudzbinu(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody Map<String, String> porudzbina) {
        KorisnikDetalji korisnikDetalji
                = korisnikDetaljiService.findByToken(StringUtils.removeStart(token, "Bearer").trim()).get();

        double iznos = 0;

        Porudzbina novaPorudzbina = new Porudzbina();
        novaPorudzbina.setAdresa(adresaRepository.findById(Long.parseLong(porudzbina.get("adresa"))).get());
        novaPorudzbina.setNacinPlacanja(nacinPlacanjaRepository.findById(Long.parseLong(porudzbina.get("nacin_placanja"))).get());
        novaPorudzbina.setBrojKartice(porudzbina.get("broj_kartice"));
        novaPorudzbina.setObradjena(false);
        novaPorudzbina.setVreme(new Date(System.currentTimeMillis()));
        novaPorudzbina.setBroj(String.valueOf(korisnikDetalji.getId()) + "-" + String.valueOf(korisnikDetalji.getPorudzbine().size() + 1));
        novaPorudzbina.setIznos(iznos);
        novaPorudzbina.setKorisnik(korisnikDetalji);
        novaPorudzbina = repository.save(novaPorudzbina);

        for (StavkaKorpe stavka : korisnikDetalji.getStavkeKorpe()) {
            PorudzbineArtikli porudzbinaArtikl = new PorudzbineArtikli();
            porudzbinaArtikl.setPorudzbinaId(novaPorudzbina.getId());
            porudzbinaArtikl.setArtiklId(stavka.getArtiklId());
            porudzbinaArtikl.setCena(stavka.getArtikl().getCena());
            porudzbinaArtikl.setKolicina(stavka.getKolicina());
            iznos += porudzbinaArtikl.getCena() * porudzbinaArtikl.getKolicina();
            porudzbinaArtiklRepository.save(porudzbinaArtikl);
            stavkaKorpeRepository.delete(stavka);
        }
        novaPorudzbina.setIznos(iznos);

        return new ResponseEntity<Porudzbina>(repository.save(novaPorudzbina), HttpStatus.OK);
    }

    @GetMapping("/user/{id}/pay/paypal")
    public ResponseEntity<String> placanjePaypal(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable("id") long id,
            @RequestParam("successUrl") String successUrl, @RequestParam("failedUrl") String failedUrl) {
        KorisnikDetalji korisnikDetalji
                = korisnikDetaljiService.findByToken(StringUtils.removeStart(token, "Bearer").trim()).get();
        Porudzbina porudzbina = repository.findById(id).get();
        if (korisnikDetalji.getId() != porudzbina.getKorisnik().getId()) {
            return new ResponseEntity<String>("", HttpStatus.NOT_FOUND);
        }

        try {
            Payment payment = paymentService.createPayment(porudzbina.getIznos() * 0.0093, "USD", "paypal",
                    "sale", porudzbina.getBroj(), failedUrl, successUrl);
            for (Links link : payment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    JsonObject json = new JsonObject();
                    json.addProperty("url", link.getHref());
                    return new ResponseEntity<String>(json.toString(), HttpStatus.OK);
                }
            }

        } catch (PayPalRESTException e) {
            return new ResponseEntity<String>("", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>("", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @GetMapping("/user/{id}/pay/bitcoin")
    @CrossOrigin
    public ResponseEntity<String> placanjeBitcoin(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable("id") long id,
            @RequestParam("successUrl") String successUrl, @RequestParam("failedUrl") String failedUrl) {
        KorisnikDetalji korisnikDetalji
                = korisnikDetaljiService.findByToken(StringUtils.removeStart(token, "Bearer").trim()).get();
        Porudzbina porudzbina = repository.findById(id).get();
        if (korisnikDetalji.getId() != porudzbina.getKorisnik().getId()) {
            return new ResponseEntity<String>("", HttpStatus.NOT_FOUND);
        }

        try {
            String url = bitcoinService.createPayment(id, porudzbina.getIznos(), successUrl, failedUrl);
        
            if (!url.isEmpty()) {
                JsonObject json = new JsonObject();
                json.addProperty("url", url);
                return new ResponseEntity<String>(json.toString(), HttpStatus.OK);
            }

        } catch (Exception e) {
            return new ResponseEntity<String>("", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>("", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @GetMapping("/user/{id}/pay/kartica")
    public ResponseEntity<String> placanjeKartica(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable("id") long id,
            @RequestParam("successUrl") String successUrl, @RequestParam("failedUrl") String failedUrl) {
        KorisnikDetalji korisnikDetalji
                = korisnikDetaljiService.findByToken(StringUtils.removeStart(token, "Bearer").trim()).get();
        Porudzbina porudzbina = repository.findById(id).get();
        if (korisnikDetalji.getId() != porudzbina.getKorisnik().getId()) {
            return new ResponseEntity<String>("", HttpStatus.NOT_FOUND);
        }

        String url = payCardService.createPayment(id, porudzbina.getIznos(), successUrl, failedUrl);
        
        if (!url.isEmpty()) {
            JsonObject json = new JsonObject();
            json.addProperty("url", url);
            return new ResponseEntity<String>(json.toString(), HttpStatus.OK);
        }
        
        return new ResponseEntity<String>("", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/user/admin/{id}")
    public ResponseEntity<Porudzbina> izmeniPorudzbinu(@PathVariable("id") Long id, @RequestBody Porudzbina porudzbina) {
        if (id != porudzbina.getId()) {
            return new ResponseEntity<Porudzbina>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Porudzbina>(repository.save(porudzbina), HttpStatus.OK);
    }

    @DeleteMapping("/user/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> obrisiArtikl(@PathVariable("id") Long id) {
        repository.deleteById(id);;
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public KorisnikDetaljiService getKorisnikDetaljiService() {
        return korisnikDetaljiService;
    }

    public void setKorisnikDetaljiService(KorisnikDetaljiService korisnikDetaljiService) {
        this.korisnikDetaljiService = korisnikDetaljiService;
    }

    public PorudzbinaRepository getRepository() {
        return repository;
    }

    public void setRepository(PorudzbinaRepository repository) {
        this.repository = repository;
    }

    public AdresaRepository getAdresaRepository() {
        return adresaRepository;
    }

    public void setAdresaRepository(AdresaRepository adresaRepository) {
        this.adresaRepository = adresaRepository;
    }

    public NacinPlacanjaRepository getNacinPlacanjaRepository() {
        return nacinPlacanjaRepository;
    }

    public void setNacinPlacanjaRepository(NacinPlacanjaRepository nacinPlacanjaRepository) {
        this.nacinPlacanjaRepository = nacinPlacanjaRepository;
    }

    public PorudzbinaArtiklRepository getPorudzbinaArtiklRepository() {
        return porudzbinaArtiklRepository;
    }

    public void setPorudzbinaArtiklRepository(PorudzbinaArtiklRepository porudzbinaArtiklRepository) {
        this.porudzbinaArtiklRepository = porudzbinaArtiklRepository;
    }

    public StavkaKorpeRepository getStavkaKorpeRepository() {
        return stavkaKorpeRepository;
    }

    public void setStavkaKorpeRepository(StavkaKorpeRepository stavkaKorpeRepository) {
        this.stavkaKorpeRepository = stavkaKorpeRepository;
    }

    public PaypalService getPaymentService() {
        return paymentService;
    }

    public void setPaymentService(PaypalService paymentService) {
        this.paymentService = paymentService;
    }

    public PayCardService getPayCardService() {
        return payCardService;
    }

    public void setPayCardService(PayCardService payCardService) {
        this.payCardService = payCardService;
    }

    public BitcoinService getBitcoinService() {
        return bitcoinService;
    }

    public void setBitcoinService(BitcoinService bitcoinService) {
        this.bitcoinService = bitcoinService;
    }
}
