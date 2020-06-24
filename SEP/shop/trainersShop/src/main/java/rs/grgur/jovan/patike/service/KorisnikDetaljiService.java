package rs.grgur.jovan.patike.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.grgur.jovan.patike.model.Korisnik;
import rs.grgur.jovan.patike.model.KorisnikDetalji;
import rs.grgur.jovan.patike.model.Uloga;
import rs.grgur.jovan.patike.repository.KorisnikRepository;
import rs.grgur.jovan.patike.repository.UlogaRepository;

@Service
public class KorisnikDetaljiService implements KorisnikService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KorisnikRepository repository;

    @Autowired
    private UlogaRepository ulogaRepository;

    @Override
    public String login(String email, String lozinka) {
        Optional<Korisnik> korisnikO = repository.login(email, lozinka);
        if (korisnikO.isPresent()) {
            String token = UUID.randomUUID().toString();
            Korisnik custom = korisnikO.get();
            custom.setToken(token);
            repository.save(custom);
            return token;
        }

        return "";
    }

    @Override
    public Optional<KorisnikDetalji> findByEmail(String email) {
        Optional<Korisnik> korisnikO = repository.findByEmail(email);
        if (korisnikO.isPresent()) {
            Korisnik korisnik = korisnikO.get();
            KorisnikDetalji korisnikDetalji = new KorisnikDetalji(korisnik);
            return Optional.of(korisnikDetalji);
        }
        return Optional.empty();
    }

    @Override
    public Optional<KorisnikDetalji> findByToken(String token) {
        Optional<Korisnik> korisnikO = repository.findByToken(token);
        if (korisnikO.isPresent()) {
            Korisnik korisnik = korisnikO.get();
            KorisnikDetalji korisnikDetalji = new KorisnikDetalji(korisnik);
            return Optional.of(korisnikDetalji);
        }
        return Optional.empty();
    }

    @Override
    public Optional<KorisnikDetalji> register(Korisnik korisnik) {
        Korisnik noviKorisnik = new Korisnik();
        noviKorisnik.setEmail(korisnik.getEmail());
        noviKorisnik.setLozinka(korisnik.getLozinka());
        noviKorisnik.setIme(korisnik.getIme());
        noviKorisnik.setPrezime(korisnik.getPrezime());
        noviKorisnik = repository.save(noviKorisnik);
        noviKorisnik.setUloge(new HashSet<Uloga>(Arrays.asList(ulogaRepository.findById((long) 1).get())));

        return Optional.of(new KorisnikDetalji(repository.save(noviKorisnik)));
    }

    @Override
    public Optional<KorisnikDetalji> save(Korisnik korisnik) {
        Korisnik noviKorisnik = repository.findById(korisnik.getId()).get();
        noviKorisnik.setEmail(korisnik.getEmail());
        noviKorisnik.setLozinka(korisnik.getLozinka());
        noviKorisnik.setIme(korisnik.getIme());
        noviKorisnik.setPrezime(korisnik.getPrezime());
        return Optional.of(new KorisnikDetalji(repository.save(noviKorisnik)));
    }
}
