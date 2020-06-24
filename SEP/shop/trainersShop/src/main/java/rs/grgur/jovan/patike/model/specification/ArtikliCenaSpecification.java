package rs.grgur.jovan.patike.model.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import rs.grgur.jovan.patike.model.Artikl;

public class ArtikliCenaSpecification implements Specification<Artikl> {

    private double cenaOd;
    private double cenaDo;

    public ArtikliCenaSpecification(double cenaOd, double cenaDo) {
        this.cenaOd = cenaOd;
        this.cenaDo = cenaDo;
    }

    @Override
    public Predicate toPredicate(Root<Artikl> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        if (cenaOd < 0 && cenaDo < 0) {
            return cb.isTrue(cb.literal(true));
        }
        if (cenaOd >= 0 && cenaDo < 0) {
            return cb.greaterThanOrEqualTo(root.get("cena"), cenaOd);
        }
        if (cenaOd < 0 && cenaDo >= 0) {
            return cb.lessThanOrEqualTo(root.get("cena"), cenaDo);
        }
        return cb.between(root.get("cena"), cenaOd, cenaDo);
    }
}
