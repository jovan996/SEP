package rs.grgur.jovan.patike.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import rs.grgur.jovan.patike.model.Boja;

@Repository
public interface BojaRepository extends JpaRepository<Boja, Long>, JpaSpecificationExecutor<Boja> {

}
