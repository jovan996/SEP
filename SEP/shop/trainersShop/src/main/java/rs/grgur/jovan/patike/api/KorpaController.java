package rs.grgur.jovan.patike.api;

import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import rs.grgur.jovan.patike.model.KorisnikDetalji;
import rs.grgur.jovan.patike.model.StavkaKorpe;
import rs.grgur.jovan.patike.repository.ArtiklRepository;
import rs.grgur.jovan.patike.repository.StavkaKorpeRepository;
import rs.grgur.jovan.patike.service.KorisnikDetaljiService;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/korpa", produces = {MediaType.APPLICATION_JSON_VALUE})
public class KorpaController {
    
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KorisnikDetaljiService korisnikDetaljiService;

    @Autowired
    private StavkaKorpeRepository stavkaKorpeRepository;
    
    @Autowired
    private ArtiklRepository artiklRepository;

    @GetMapping("/user")
    public ResponseEntity<Set<StavkaKorpe>> preuzmiStavke(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        KorisnikDetalji korisnikDetalji
                = korisnikDetaljiService.findByToken(StringUtils.removeStart(token, "Bearer").trim()).get();

        return new ResponseEntity<Set<StavkaKorpe>>(korisnikDetalji.getStavkeKorpe(), HttpStatus.OK);
    }

    @PostMapping("/user")
    public ResponseEntity<StavkaKorpe> dodajStavku(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody Map<String, String> stavkaKorpe) {
        KorisnikDetalji korisnikDetalji
                = korisnikDetaljiService.findByToken(StringUtils.removeStart(token, "Bearer").trim()).get();

        StavkaKorpe novaStavka = new StavkaKorpe();
        boolean stavkaPostoji = false;
        long artiklId = Long.parseLong(stavkaKorpe.get("artikl"));

        for (StavkaKorpe stavka : korisnikDetalji.getStavkeKorpe()) {
            if (stavka.getArtiklId()== artiklId) {
                novaStavka = stavka;
                novaStavka.setKorisnikId(korisnikDetalji.getId());
                novaStavka.setKolicina(novaStavka.getKolicina() + Integer.parseInt(stavkaKorpe.get("kolicina")));
                novaStavka.setVelicina(Integer.parseInt(stavkaKorpe.get("velicina")));
                stavkaPostoji = true;
                break;
            }
        }

        if (!stavkaPostoji) {
            novaStavka.setArtiklId(artiklId);
            novaStavka.setKolicina(Integer.parseInt(stavkaKorpe.get("kolicina")));
            novaStavka.setVelicina(Integer.parseInt(stavkaKorpe.get("velicina")));
            novaStavka.setKorisnikId(korisnikDetalji.getId());
        }

        return new ResponseEntity<StavkaKorpe>(stavkaKorpeRepository.save(novaStavka), HttpStatus.OK);
    }

    @PutMapping("/user/{artiklId}")
    public ResponseEntity<StavkaKorpe> izmeniStavku(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable("artiklId") long artiklId, @RequestBody Map<String, String> stavkaKorpe) {
        KorisnikDetalji korisnikDetalji
                = korisnikDetaljiService.findByToken(StringUtils.removeStart(token, "Bearer").trim()).get();
        boolean stavkaKorisnika = false;
        StavkaKorpe novaStavka = new StavkaKorpe();

        for (StavkaKorpe stavka : korisnikDetalji.getStavkeKorpe()) {
            if (stavka.getArtiklId() == artiklId) {
                stavkaKorisnika = true;
                novaStavka = stavka;
                novaStavka.setKolicina(Integer.parseInt(stavkaKorpe.get("kolicina")));
                novaStavka.setVelicina(Integer.parseInt(stavkaKorpe.get("velicina")));
                break;
            }
        }
        if (!stavkaKorisnika) {
            return new ResponseEntity<StavkaKorpe>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<StavkaKorpe>(stavkaKorpeRepository.save(novaStavka), HttpStatus.OK);
    }

    @DeleteMapping("/user/{artiklId}")
    public ResponseEntity<Void> obrisiStavku(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable("artiklId") long artiklId) {
        KorisnikDetalji korisnikDetalji
                = korisnikDetaljiService.findByToken(StringUtils.removeStart(token, "Bearer").trim()).get();

        for (StavkaKorpe stavkaKrope : korisnikDetalji.getStavkeKorpe()) {
            if (stavkaKrope.getArtikl().getId() == artiklId) {
                stavkaKorpeRepository.delete(stavkaKrope);
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

    public StavkaKorpeRepository getStavkaKorpeRepository() {
        return stavkaKorpeRepository;
    }

    public void setStavkaKorpeRepository(StavkaKorpeRepository stavkaKorpeRepository) {
        this.stavkaKorpeRepository = stavkaKorpeRepository;
    }
}
