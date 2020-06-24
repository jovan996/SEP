package rs.grgur.jovan.optika.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import rs.grgur.jovan.optika.model.Business;

@Repository
public interface BusinessRepository extends JpaRepository<Business, String>, JpaSpecificationExecutor<Business> {

}
