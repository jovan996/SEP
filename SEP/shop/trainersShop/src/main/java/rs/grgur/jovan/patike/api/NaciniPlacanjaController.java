package rs.grgur.jovan.patike.api;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.grgur.jovan.patike.model.NacinPlacanja;
import rs.grgur.jovan.patike.repository.NacinPlacanjaRepository;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/nacini_placanja", produces = {MediaType.APPLICATION_JSON_VALUE})
public class NaciniPlacanjaController {

    @Autowired
    private NacinPlacanjaRepository repository;

    @GetMapping(value = "")
    public List<NacinPlacanja> preuzmiBoje() {
        return repository.findAll();
    }

    public NacinPlacanjaRepository getRepository() {
        return repository;
    }

    public void setRepository(NacinPlacanjaRepository repository) {
        this.repository = repository;
    }
}
