package rs.grgur.jovan.patike.api;

import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.grgur.jovan.patike.model.Adresa;
import rs.grgur.jovan.patike.model.KorisnikDetalji;
import rs.grgur.jovan.patike.repository.AdresaRepository;
import rs.grgur.jovan.patike.service.KorisnikDetaljiService;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/adrese", produces = {MediaType.APPLICATION_JSON_VALUE})
public class AdreseController {

    @Autowired
    private KorisnikDetaljiService korisnikDetaljiService;

    @Autowired
    private AdresaRepository adresaRepository;

    @GetMapping("/user")
    public ResponseEntity<Set<Adresa>> preuzmiAdrese(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        KorisnikDetalji korisnikDetalji
                = korisnikDetaljiService.findByToken(StringUtils.removeStart(token, "Bearer").trim()).get();

        return new ResponseEntity<Set<Adresa>>(korisnikDetalji.getAdrese(), HttpStatus.OK);
    }
    
    @GetMapping("/user/{id}")
    public ResponseEntity<Adresa>  preuzmiAdresu(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable("id") long id) {
        KorisnikDetalji korisnikDetalji
                = korisnikDetaljiService.findByToken(StringUtils.removeStart(token, "Bearer").trim()).get();

        for (Adresa adresa : korisnikDetalji.getAdrese()) {
            if (adresa.getId() == id) {
                return new ResponseEntity<Adresa>(adresa, HttpStatus.OK);
            }
        }

        return new ResponseEntity<Adresa>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/user")
    public ResponseEntity<Adresa> dodajAdresu(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody Adresa adresa) {
        KorisnikDetalji korisnikDetalji
                = korisnikDetaljiService.findByToken(StringUtils.removeStart(token, "Bearer").trim()).get();
        Adresa novaAdresa = new Adresa();
        novaAdresa.setMesto(adresa.getMesto());
        novaAdresa.setPostanskiBroj(adresa.getPostanskiBroj());
        novaAdresa.setAdresa(adresa.getAdresa());
        novaAdresa.setKorisnik(korisnikDetalji);
        return new ResponseEntity<Adresa>(adresaRepository.save(novaAdresa), HttpStatus.OK);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<Adresa> izmeniAdresu(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable("id") long id, @RequestBody Adresa adresa) {
        KorisnikDetalji korisnikDetalji
                = korisnikDetaljiService.findByToken(StringUtils.removeStart(token, "Bearer").trim()).get();
        boolean adresaKorisnika = false;
        Adresa novaAdresa = adresa;

        for (Adresa adresaTmp : korisnikDetalji.getAdrese()) {
            if (adresaTmp.getId() == id) {
                adresaKorisnika = true;
                novaAdresa.setKorisnik(korisnikDetalji);
                break;
            }
        }
        if (!adresaKorisnika) {
            return new ResponseEntity<Adresa>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Adresa>(adresaRepository.save(novaAdresa), HttpStatus.OK);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> obrisiAdresu(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable("id") long id) {
        KorisnikDetalji korisnikDetalji
                = korisnikDetaljiService.findByToken(StringUtils.removeStart(token, "Bearer").trim()).get();

        for (Adresa adresa : korisnikDetalji.getAdrese()) {
            if (adresa.getId() == id) {
                adresaRepository.deleteById(id);
                return new ResponseEntity<Void>(HttpStatus.OK);
            }
        }

        return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
    }

    public KorisnikDetaljiService getKorisnikDetaljiService() {
        return korisnikDetaljiService;
    }

    public void setKorisnikDetaljiService(KorisnikDetaljiService korisnikDetaljiService) {
        this.korisnikDetaljiService = korisnikDetaljiService;
    }

    public AdresaRepository getAdresaRepository() {
        return adresaRepository;
    }

    public void setAdresaRepository(AdresaRepository adresaRepository) {
        this.adresaRepository = adresaRepository;
    }
}
