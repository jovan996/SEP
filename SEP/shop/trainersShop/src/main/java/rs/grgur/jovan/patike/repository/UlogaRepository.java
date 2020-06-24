package rs.grgur.jovan.patike.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import rs.grgur.jovan.patike.model.Uloga;

@Repository
public interface UlogaRepository extends JpaRepository<Uloga, Long>, JpaSpecificationExecutor<Uloga> {

}
