package rs.grgur.jovan.patike.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import rs.grgur.jovan.patike.model.Adresa;

@Repository
public interface AdresaRepository extends JpaRepository<Adresa, Long>, JpaSpecificationExecutor<Adresa> {

}
