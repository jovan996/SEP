package rs.grgur.jovan.patike.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import rs.grgur.jovan.patike.model.Brend;

@Repository
public interface BrendRepository extends JpaRepository<Brend, Long>, JpaSpecificationExecutor<Brend> {

}
