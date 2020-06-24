package rs.grgur.jovan.patike.api;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rs.grgur.jovan.patike.model.Artikl;
import rs.grgur.jovan.patike.model.specification.ArtikliBojaSpecification;
import rs.grgur.jovan.patike.model.specification.ArtikliBrendSpecification;
import rs.grgur.jovan.patike.model.specification.ArtikliCenaSpecification;
import rs.grgur.jovan.patike.model.specification.ArtikliPolSpecification;
import rs.grgur.jovan.patike.model.specification.ArtikliVrstaSpecification;
import rs.grgur.jovan.patike.repository.ArtiklRepository;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/artikli", produces = {MediaType.APPLICATION_JSON_VALUE})
public class ArtikliController {
    
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private ArtiklRepository repository;

    @GetMapping()
    public ResponseEntity<List<Artikl>> preuzmiArtikle(@RequestParam Map<String, String> allParams) {
        if (allParams.isEmpty()) {
            return new ResponseEntity<List<Artikl>>(repository.findAll(), HttpStatus.OK);
        }

        int vrsta = allParams.containsKey("vrsta") ? Integer.parseInt(allParams.get("vrsta")) : -1;
        int brend = allParams.containsKey("brend") ? Integer.parseInt(allParams.get("brend")) : -1;
        int boja = allParams.containsKey("boja") ? Integer.parseInt(allParams.get("boja")) : -1;
        String pol = allParams.containsKey("pol") ? allParams.get("pol") : "";
        double cenaOd = allParams.containsKey("cenaOd") ? Double.parseDouble(allParams.get("cenaOd")) : -1;
        double cenaDo = allParams.containsKey("cenaDo") ? Double.parseDouble(allParams.get("cenaDo")) : -1;

        Specification<Artikl> spec = Specification.where(new ArtikliVrstaSpecification(vrsta))
                .and(new ArtikliBrendSpecification(brend))
                .and(new ArtikliBojaSpecification(boja))
                .and(new ArtikliPolSpecification(pol))
                .and(new ArtikliCenaSpecification(cenaOd, cenaDo));

        return new ResponseEntity<List<Artikl>>(repository.findAll(spec), HttpStatus.OK);
    }

    @GetMapping("/istaknuti")
    public ResponseEntity<List<Artikl>> preuzmiIstaknuteArtikle() {
        return new ResponseEntity<List<Artikl>>(repository.findFeatured(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Artikl> preuzmiArtikl(@PathVariable("id") long id) {
        return new ResponseEntity<Artikl>(repository.findById(id).get(), HttpStatus.OK);
    }

    @PostMapping("/user")
    public ResponseEntity<Artikl> dodajArtikl(@RequestBody Artikl artikl) {
        Artikl noviArtikl = new Artikl();
        noviArtikl.setNaziv(artikl.getNaziv());
        noviArtikl.setOpis(artikl.getOpis());
        noviArtikl.setCena(artikl.getCena());
        noviArtikl.setIstaknut(artikl.isIstaknut());
        noviArtikl.setBoja(artikl.getBoja());
        noviArtikl.setBrend(artikl.getBrend());
        noviArtikl.setVrsta(artikl.getVrsta());
        return new ResponseEntity<Artikl>(repository.save(noviArtikl), HttpStatus.OK);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<Artikl> izmeniArtikl(@PathVariable("id") Long id, @RequestBody Artikl artikl) {
        if (id != artikl.getId()) {
            return new ResponseEntity<Artikl>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Artikl>(repository.save(artikl), HttpStatus.OK);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> obrisiArtikl(@PathVariable("id") Long id) {
        repository.deleteById(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ArtiklRepository getRepository() {
        return repository;
    }

    public void setRepository(ArtiklRepository repository) {
        this.repository = repository;
    }
}
