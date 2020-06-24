package rs.grgur.jovan.patike.api;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.grgur.jovan.patike.model.Boja;
import rs.grgur.jovan.patike.repository.BojaRepository;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/boje", produces = {MediaType.APPLICATION_JSON_VALUE})
public class BojeController {

    @Autowired
    private BojaRepository repository;

    @GetMapping(value = "")
    public List<Boja> preuzmiBoje() {
        return repository.findAll();
    }

    public BojaRepository getRepository() {
        return repository;
    }

    public void setRepository(BojaRepository repository) {
        this.repository = repository;
    }
}
