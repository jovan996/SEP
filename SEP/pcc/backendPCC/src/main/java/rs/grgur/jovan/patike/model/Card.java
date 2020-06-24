package rs.grgur.jovan.patike.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "cards")
public class Card implements Serializable {

    @Id
    @Column(name = "pan", unique = true)
    @Size(max = 16)
    private String pan;
    
    @Column(name = "bank_url")
    @Size(max = 100)
    private String bankUrl;

    public Card() {
    }
    
    public Card(Card card) {
        this.pan = card.pan;
        this.bankUrl = card.bankUrl;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getBankUrl() {
        return bankUrl;
    }

    public void setBankUrl(String bankUrl) {
        this.bankUrl = bankUrl;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.pan);
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
        final Card other = (Card) obj;
        if (!Objects.equals(this.pan, other.pan)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Card{" + "pan=" + pan + ", bankUrl=" + bankUrl + '}';
    }
}
