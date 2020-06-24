package rs.grgur.jovan.patike.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "korpa")
@IdClass(StavkaKorpePK.class)
public class StavkaKorpe implements Serializable {

    @Id
    @Column(name = "korisnici_id")
    private long korisnikId;

    @Id
    @Column(name = "artikli_id")
    private long artiklId;

    @ManyToOne
    @JoinColumn(name = "korisnici_id", insertable = false, updatable = false)
    @JsonIgnore
    private Korisnik korisnik;

    @ManyToOne
    @JoinColumn(name = "artikli_id", insertable = false, updatable = false)
    private Artikl artikl;

    @Column(name = "kolicina")
    private int kolicina;

    @Column(name = "velicina")
    private int velicina;

    public StavkaKorpe() {
    }

    public long getKorisnikId() {
        return korisnikId;
    }

    public void setKorisnikId(long korisnikId) {
        this.korisnikId = korisnikId;
    }

    public long getArtiklId() {
        return artiklId;
    }

    public void setArtiklId(long artiklId) {
        this.artiklId = artiklId;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public Artikl getArtikl() {
        return artikl;
    }

    public void setArtikl(Artikl artikl) {
        this.artikl = artikl;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public int getVelicina() {
        return velicina;
    }

    public void setVelicina(int velicina) {
        this.velicina = velicina;
    }

    @Override
    public String toString() {
        return "StavkaKorpe{" + "korisnik=" + korisnik + ", artikl=" + artikl + ", kolicina=" + kolicina + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + Objects.hashCode(this.korisnik);
        hash = 13 * hash + Objects.hashCode(this.artikl);
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
        final StavkaKorpe other = (StavkaKorpe) obj;
        if (!Objects.equals(this.korisnik, other.korisnik)) {
            return false;
        }
        if (!Objects.equals(this.artikl, other.artikl)) {
            return false;
        }
        return true;
    }
}
