package rs.grgur.jovan.patike.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rs.grgur.jovan.patike.model.Artikl;

@Repository
public interface ArtiklRepository extends JpaRepository<Artikl, Long>, JpaSpecificationExecutor<Artikl> {

    @Query("FROM #{#entityName} WHERE istaknut=1")
    public List findFeatured();

    @Query("FROM #{#entityName} WHERE vrste_id=?1 AND brendovi_id=?2 AND boje_id=?3 AND pol=?4")
    public List findFiltered(int vrsta, int brend, int boja, int pol);

    @Query("FROM #{#entityName} WHERE vrste_id=?1 AND brendovi_id=?2 AND boje_id=?3 AND pol=?4 AND cena>=?5 AND cena<=?6")
    public List findFiltered(String vrsta, int brend, int boja, int pol, double cenaMin, double cenaMax);
}
