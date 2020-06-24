package rs.grgur.jovan.patike.model.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import rs.grgur.jovan.patike.model.Artikl;

public class ArtikliBojaSpecification implements Specification<Artikl> {

    private int bojaId;

    public ArtikliBojaSpecification(int bojaId) {
        this.bojaId = bojaId;
    }

    @Override
    public Predicate toPredicate(Root<Artikl> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        if (bojaId < 0) {
            return cb.isTrue(cb.literal(true));
        }
        return cb.equal(root.get("boja"), this.bojaId);
    }
}
