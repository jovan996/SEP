package rs.grgur.jovan.patike.model;

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
@Table(name = "porudzbine_artikli")
@IdClass(PorudzbineArtikliPK.class)
public class PorudzbineArtikli implements Serializable {

    @Id
    @Column(name = "porudzbine_id")
    private long porudzbinaId;

    @Id
    @Column(name = "artikli_id")
    private long artiklId;

    @ManyToOne
    @JoinColumn(name = "artikli_id", insertable = false, updatable = false)
    private Artikl artikl;

    @Column(name = "cena")
    private double cena;

    @Column(name = "kolicina")
    private int kolicina;

    @Column(name = "valicina")
    private int valicina;

    @Column(name = "iznos")
    private double iznos;

    @Column(name = "velicina")
    private int velicina;

    public PorudzbineArtikli() {
    }

    public long getPorudzbinaId() {
        return porudzbinaId;
    }

    public void setPorudzbinaId(long porudzbinaId) {
        this.porudzbinaId = porudzbinaId;
    }

    public long getArtiklId() {
        return artiklId;
    }

    public void setArtiklId(long artiklId) {
        this.artiklId = artiklId;
    }

    public Artikl getArtikl() {
        return artikl;
    }

    public void setArtikl(Artikl artikl) {
        this.artikl = artikl;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public double getIznos() {
        return iznos;
    }

    public void setIznos(double iznos) {
        this.iznos = iznos;
    }

    public int getValicina() {
        return valicina;
    }

    public void setValicina(int valicina) {
        this.valicina = valicina;
    }

    public int getVelicina() {
        return velicina;
    }

    public void setVelicina(int velicina) {
        this.velicina = velicina;
    }

    @Override
    public String toString() {
        return "PorudzbineArtikli{" + "porudzbina=" + porudzbinaId + ", artikl=" + artiklId + ", cena=" + cena + ", kolicina=" + kolicina + ", iznos=" + iznos + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.porudzbinaId);
        hash = 37 * hash + Objects.hashCode(this.artiklId);
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
        final PorudzbineArtikli other = (PorudzbineArtikli) obj;
        if (!Objects.equals(this.porudzbinaId, other.porudzbinaId)) {
            return false;
        }
        if (!Objects.equals(this.artiklId, other.artiklId)) {
            return false;
        }
        return true;
    }
}
