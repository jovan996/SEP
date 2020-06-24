package rs.grgur.jovan.patike.api;

import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.grgur.jovan.patike.model.Korisnik;
import rs.grgur.jovan.patike.model.KorisnikDetalji;
import rs.grgur.jovan.patike.service.KorisnikDetaljiService;

@RestController
@CrossOrigin
@RequestMapping(value = "/login", produces = {MediaType.APPLICATION_JSON_VALUE})
public class LoginController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KorisnikDetaljiService korisnikDetaljiService;

    @PostMapping()
    public ResponseEntity<KorisnikDetalji> login(@RequestBody Korisnik korisnik) {
        String token = korisnikDetaljiService.login(korisnik.getEmail(), korisnik.getLozinka());
        if (StringUtils.isEmpty(token)) {
            return new ResponseEntity<KorisnikDetalji>(HttpStatus.FORBIDDEN);
        }

        KorisnikDetalji korisnikDetalji = korisnikDetaljiService.findByToken(token).get();
        korisnikDetalji.setLozinka("");
        return new ResponseEntity<KorisnikDetalji>(korisnikDetalji, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<KorisnikDetalji> register(@RequestBody Korisnik korisnik) {
        Optional<KorisnikDetalji> korisnik2 = korisnikDetaljiService.findByEmail(korisnik.getEmail());
        if (korisnik2.isPresent()) {
            return new ResponseEntity<KorisnikDetalji>(HttpStatus.FORBIDDEN);
        }

        KorisnikDetalji noviKorisnik = korisnikDetaljiService.register(korisnik).get();

        String token = korisnikDetaljiService.login(noviKorisnik.getEmail(), noviKorisnik.getLozinka());
        if (StringUtils.isEmpty(token)) {
            return new ResponseEntity<KorisnikDetalji>(HttpStatus.FORBIDDEN);
        }

        KorisnikDetalji korisnikDetalji = korisnikDetaljiService.findByToken(token).get();
        korisnikDetalji.setLozinka("");
        return new ResponseEntity<KorisnikDetalji>(korisnikDetalji, HttpStatus.OK);
    }
}
