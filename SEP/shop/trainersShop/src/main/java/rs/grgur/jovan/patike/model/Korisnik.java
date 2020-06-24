package rs.grgur.jovan.patike.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "korisnici")
public class Korisnik implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "email", unique = true)
    @NotNull
    @Size(max = 45)
    private String email;

    @Column(name = "lozinka")
    @Size(max = 64)
    private String lozinka;

    @Column(name = "ime")
    @Size(max = 45)
    private String ime;

    @Column(name = "prezime")
    @Size(max = 45)
    private String prezime;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "korisnici_uloge", joinColumns = @JoinColumn(name = "korisnici_id"), inverseJoinColumns = @JoinColumn(name = "uloge_id"))
    private Set<Uloga> uloge;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "korisnici_id")
    @JsonIgnore
    private Set<Adresa> adrese;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "korisnici_id")
    @JsonIgnore
    private Set<StavkaKorpe> stavkeKorpe;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "korisnik")
    @JsonIgnore
    private Set<Porudzbina> porudzbine;

    @Column(name = "token")
    private String token;
    
    @Column(name = "sredstva")
    private double sredstva;

    public Korisnik() {
    }

    public Korisnik(Korisnik korisnik) {
        this.id = korisnik.getId();
        this.email = korisnik.getEmail();
        this.lozinka = korisnik.getLozinka();
        this.ime = korisnik.getIme();
        this.prezime = korisnik.getPrezime();
        this.uloge = korisnik.getUloge();
        this.adrese = korisnik.getAdrese();
        this.stavkeKorpe = korisnik.getStavkeKorpe();
        this.porudzbine = korisnik.getPorudzbine();
        this.token = korisnik.getToken();
        this.sredstva = korisnik.getSredstva();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonIgnore
    public String getLozinka() {
        return lozinka;
    }

    @JsonProperty
    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public Set<Uloga> getUloge() {
        return uloge;
    }

    public void setUloge(Set<Uloga> uloge) {
        this.uloge = uloge;
    }

    public Set<Adresa> getAdrese() {
        return adrese;
    }

    public void setAdrese(Set<Adresa> adrese) {
        this.adrese = adrese;
    }

    public Set<StavkaKorpe> getStavkeKorpe() {
        return stavkeKorpe;
    }

    public void setStavkeKorpe(Set<StavkaKorpe> stavkeKorpe) {
        this.stavkeKorpe = stavkeKorpe;
    }

    public Set<Porudzbina> getPorudzbine() {
        return porudzbine;
    }

    public void setPorudzbine(Set<Porudzbina> porudzbine) {
        this.porudzbine = porudzbine;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public double getSredstva() {
        return sredstva;
    }

    public void setSredstva(double sredstva) {
        this.sredstva = sredstva;
    }

    @Override
    public String toString() {
        return "Korisnik{" + "id=" + id + ", email=" + email + ", ime=" + ime + ", prezime=" + prezime + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.email);
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
        final Korisnik other = (Korisnik) obj;
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        return true;
    }
}
