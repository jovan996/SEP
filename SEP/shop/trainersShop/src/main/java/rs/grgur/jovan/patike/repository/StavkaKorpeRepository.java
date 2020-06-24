package rs.grgur.jovan.patike.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import rs.grgur.jovan.patike.model.StavkaKorpe;

@Repository
public interface StavkaKorpeRepository extends JpaRepository<StavkaKorpe, Long>, JpaSpecificationExecutor<StavkaKorpe> {

}
