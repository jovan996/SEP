package rs.grgur.jovan.patike.model.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import rs.grgur.jovan.patike.model.Artikl;

public class ArtikliPolSpecification implements Specification<Artikl> {

    private String pol;

    public ArtikliPolSpecification(String pol) {
        this.pol = pol;
    }

    @Override
    public Predicate toPredicate(Root<Artikl> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        if (pol.isEmpty()) {
            return cb.isTrue(cb.literal(true));
        }
        return cb.equal(root.get("pol"), pol);
    }
}
