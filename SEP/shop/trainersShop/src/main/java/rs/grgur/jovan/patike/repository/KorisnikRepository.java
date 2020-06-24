package rs.grgur.jovan.patike.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rs.grgur.jovan.patike.model.Korisnik;

@Repository
public interface KorisnikRepository extends JpaRepository<Korisnik, Long>, JpaSpecificationExecutor<Korisnik> {

    public Optional<Korisnik> findByEmail(String username);

    @Query(value = "FROM #{#entityName} WHERE email=?1 AND lozinka=?2")
    public Optional<Korisnik> login(String email, String lozinka);

    @Query(value = "FROM #{#entityName} WHERE token=?1")
    public Optional<Korisnik> findByToken(String token);
}
