package rs.grgur.jovan.patike.model;

import java.io.Serializable;
import javax.persistence.Column;

public class PorudzbineArtikliPK implements Serializable {

    @Column(name = "porudzbine_id")
    private long porudzbinaId;

    @Column(name = "artikli_id")
    private long artiklId;

    public PorudzbineArtikliPK() {
    }

    public Long getPorudzbinaId() {
        return porudzbinaId;
    }

    public void setPorudzbinaId(Long porudzbinaId) {
        this.porudzbinaId = porudzbinaId;
    }

    public Long getArtiklId() {
        return artiklId;
    }

    public void setArtiklId(Long artiklId) {
        this.artiklId = artiklId;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + (int) (this.porudzbinaId ^ (this.porudzbinaId >>> 32));
        hash = 79 * hash + (int) (this.artiklId ^ (this.artiklId >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PorudzbineArtikliPK other = (PorudzbineArtikliPK) obj;
        if (this.porudzbinaId != other.porudzbinaId) {
            return false;
        }
        if (this.artiklId != other.artiklId) {
            return false;
        }
        return true;
    }
}
