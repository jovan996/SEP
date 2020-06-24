package rs.grgur.jovan.patike.api;

import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.grgur.jovan.patike.model.Korisnik;
import rs.grgur.jovan.patike.model.KorisnikDetalji;
import rs.grgur.jovan.patike.service.KorisnikDetaljiService;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/korisnici", produces = {MediaType.APPLICATION_JSON_VALUE})
public class KorisnikController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KorisnikDetaljiService korisnikDetaljiService;

    @GetMapping("/user")
    public ResponseEntity<KorisnikDetalji> preuzmiKorisnika(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        Optional<KorisnikDetalji> korisnikDetaljiO
                = korisnikDetaljiService.findByToken(StringUtils.removeStart(token, "Bearer").trim());
        if (!korisnikDetaljiO.isPresent()) {
            return new ResponseEntity<KorisnikDetalji>(HttpStatus.FORBIDDEN);
        }

        KorisnikDetalji korisnikDetalji = korisnikDetaljiO.get();
        korisnikDetalji.setLozinka("");
        return new ResponseEntity<KorisnikDetalji>(korisnikDetalji, HttpStatus.OK);
    }

    @PostMapping("/user/{id}")
    public ResponseEntity<KorisnikDetalji> izmeniKorisnika(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable("id") Long id, @RequestBody Korisnik korisnik) {
        Optional<KorisnikDetalji> korisnikDetaljiO
                = korisnikDetaljiService.findByToken(StringUtils.removeStart(token, "Bearer").trim());
        if (!korisnikDetaljiO.isPresent() || korisnikDetaljiO.get().getId() != id) {
            return new ResponseEntity<KorisnikDetalji>(HttpStatus.FORBIDDEN);
        }

        KorisnikDetalji korisnikDetalji = korisnikDetaljiO.get();
        korisnikDetalji.setIme(korisnik.getIme());
        korisnikDetalji.setPrezime(korisnik.getPrezime());
        korisnikDetalji.setEmail(korisnik.getEmail());
        if (korisnikDetalji.getLozinka().length() > 0) {
            korisnikDetalji.setLozinka(korisnik.getLozinka());
        }

        return new ResponseEntity<KorisnikDetalji>(korisnikDetaljiService.save(korisnikDetalji).get(), HttpStatus.OK);
    }
}
