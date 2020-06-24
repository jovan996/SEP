package rs.grgur.jovan.patike.model;

import java.io.Serializable;
import javax.persistence.Column;

public class StavkaKorpePK implements Serializable {

    @Column(name = "korisnici_id")
    private long korisnikId;

    @Column(name = "artikli_id")
    private long artiklId;

    public StavkaKorpePK() {
    }

    public Long getKorisnikId() {
        return korisnikId;
    }

    public void setKorisnikId(Long korisnikId) {
        this.korisnikId = korisnikId;
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
        hash = 79 * hash + (int) (this.korisnikId ^ (this.korisnikId >>> 32));
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
        final StavkaKorpePK other = (StavkaKorpePK) obj;
        if (this.korisnikId != other.korisnikId) {
            return false;
        }
        if (this.artiklId != other.artiklId) {
            return false;
        }
        return true;
    }
}
