package rs.grgur.jovan.patike.api;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.grgur.jovan.patike.model.Vrsta;
import rs.grgur.jovan.patike.repository.VrstaRepository;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/vrste", produces = {MediaType.APPLICATION_JSON_VALUE})
public class VrsteController {

    @Autowired
    private VrstaRepository repository;

    @GetMapping(value = "")
    public List<Vrsta> preuzmiVrste() {
        return repository.findAll();
    }

    public VrstaRepository getRepository() {
        return repository;
    }

    public void setRepository(VrstaRepository repository) {
        this.repository = repository;
    }
}
