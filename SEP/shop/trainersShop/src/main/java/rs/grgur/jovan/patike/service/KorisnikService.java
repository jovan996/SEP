package rs.grgur.jovan.patike.service;

import java.util.Optional;
import rs.grgur.jovan.patike.model.Korisnik;

public interface KorisnikService {

    public String login(String email, String lozinka);

    public Optional findByEmail(String email);

    public Optional findByToken(String token);

    public Optional register(Korisnik korisnik);

    public Optional save(Korisnik korisnik);
}
