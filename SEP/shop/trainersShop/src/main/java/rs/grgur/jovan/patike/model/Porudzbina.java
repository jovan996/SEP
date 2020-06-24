package rs.grgur.jovan.patike.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "porudzbine")
public class Porudzbina implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "broj", unique = true)
    @NotNull
    @Size(max = 45)
    private String broj;

    @Column(name = "vreme")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vreme;

    @Column(name = "iznos")
    private double iznos;

    @Column(name = "broj_kartice")
    @Size(max = 45)
    private String brojKartice;

    @Column(name = "obradjena")
    private boolean obradjena;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "nacini_placanja_id")
    private NacinPlacanja nacinPlacanja;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "adrese_id")
    private Adresa adresa;

    @ManyToOne()
    @JoinColumn(name = "korisnici_id")
    private Korisnik korisnik;

    @OneToMany
    @JoinColumn(name = "porudzbine_id")
    private Set<PorudzbineArtikli> porudzbineArtikli;
    
    @Column(name = "placen")
    private boolean placeno;

    public Porudzbina() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBroj() {
        return broj;
    }

    public void setBroj(String broj) {
        this.broj = broj;
    }

    public Date getVreme() {
        return vreme;
    }

    public void setVreme(Date vreme) {
        this.vreme = vreme;
    }

    public double getIznos() {
        return iznos;
    }

    public void setIznos(double iznos) {
        this.iznos = iznos;
    }

    public String getBrojKartice() {
        return brojKartice;
    }

    public void setBrojKartice(String brojKartice) {
        this.brojKartice = brojKartice;
    }

    public boolean isObradjena() {
        return obradjena;
    }

    public void setObradjena(boolean obradjena) {
        this.obradjena = obradjena;
    }

    public NacinPlacanja getNacinPlacanja() {
        return nacinPlacanja;
    }

    public void setNacinPlacanja(NacinPlacanja nacinPlacanja) {
        this.nacinPlacanja = nacinPlacanja;
    }

    public Adresa getAdresa() {
        return adresa;
    }

    public void setAdresa(Adresa adresa) {
        this.adresa = adresa;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public Set<PorudzbineArtikli> getPorudzbineArtikli() {
        return porudzbineArtikli;
    }

    public void setPorudzbineArtikli(Set<PorudzbineArtikli> porudzbineArtikli) {
        this.porudzbineArtikli = porudzbineArtikli;
    }

    public boolean isPlaceno() {
        return placeno;
    }

    public void setPlaceno(boolean placeno) {
        this.placeno = placeno;
    }

    @Override
    public String toString() {
        return "Porudzbina{" + "id=" + id + ", broj=" + broj + ", vreme=" + vreme + ", iznos=" + iznos + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (int) (this.id ^ (this.id >>> 32));
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
        final Porudzbina other = (Porudzbina) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
}
