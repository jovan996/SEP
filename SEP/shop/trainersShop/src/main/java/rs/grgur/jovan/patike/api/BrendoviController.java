package rs.grgur.jovan.patike.api;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.grgur.jovan.patike.model.Brend;
import rs.grgur.jovan.patike.repository.BrendRepository;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/brendovi", produces = {MediaType.APPLICATION_JSON_VALUE})
public class BrendoviController {

    @Autowired
    private BrendRepository repository;

    @GetMapping(value = "")
    public List<Brend> preuzmiBrendove() {
        return repository.findAll();
    }

    public BrendRepository getRepository() {
        return repository;
    }

    public void setRepository(BrendRepository repository) {
        this.repository = repository;
    }
}
