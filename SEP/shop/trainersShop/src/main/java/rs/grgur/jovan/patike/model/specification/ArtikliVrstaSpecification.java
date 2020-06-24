package rs.grgur.jovan.patike.model.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import rs.grgur.jovan.patike.model.Artikl;

public class ArtikliVrstaSpecification implements Specification<Artikl> {

    private int vrstaId;

    public ArtikliVrstaSpecification(int vrstaId) {
        this.vrstaId = vrstaId;
    }

    @Override
    public Predicate toPredicate(Root<Artikl> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        if (vrstaId < 0) {
            return cb.isTrue(cb.literal(true));
        }
        return cb.equal(root.get("vrsta"), this.vrstaId);
    }
}
