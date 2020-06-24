package rs.grgur.jovan.patike.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "artikli")
public class Artikl implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "naziv")
    @Size(max = 45)
    private String naziv;

    @Column(name = "pol")
    @Size(max = 1)
    private String pol;

    @Column(name = "opis")
    private String opis;

    @Column(name = "cena")
    private double cena;

    @Column(name = "istaknut")
    private boolean istaknut;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "boje_id")
    private Boja boja;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "brendovi_id")
    private Brend brend;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vrste_id")
    private Vrsta vrsta;

    public Artikl() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getPol() {
        return pol;
    }

    public void setPol(String pol) {
        this.pol = pol;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public Boja getBoja() {
        return boja;
    }

    public void setBoja(Boja boja) {
        this.boja = boja;
    }

    public Brend getBrend() {
        return brend;
    }

    public void setBrend(Brend brend) {
        this.brend = brend;
    }

    public Vrsta getVrsta() {
        return vrsta;
    }

    public void setVrsta(Vrsta vrste) {
        this.vrsta = vrste;
    }

    public boolean isIstaknut() {
        return istaknut;
    }

    public void setIstaknut(boolean istaknut) {
        this.istaknut = istaknut;
    }

    @Override
    public String toString() {
        return "Artikl{" + "id=" + id + ", naziv=" + naziv + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (int) (this.id ^ (this.id >>> 32));
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
        final Artikl other = (Artikl) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
}
