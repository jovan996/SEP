package rs.grgur.jovan.patike.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import rs.grgur.jovan.patike.model.Vrsta;

@Repository
public interface VrstaRepository extends JpaRepository<Vrsta, Long> , JpaSpecificationExecutor<Vrsta>{

}
