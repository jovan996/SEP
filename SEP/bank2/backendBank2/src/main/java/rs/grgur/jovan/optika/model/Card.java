package rs.grgur.jovan.optika.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "cards")
public class Card implements Serializable {

    @Id
    @Column(name = "pan", unique = true)
    @Size(max = 16)
    private String pan;
    
    @Column(name = "pin")
    @Size(max = 4)
    @NotNull
    private String pin;
    
    @Column(name = "issue_date")
    @NotNull
    private Date issueDate;
    
    @Column(name = "expiry_date")
    @NotNull
    private Date expiryDate;
    
    @Column(name = "cardholderName")
    @Size(max = 100)
    private String cardholderName;
    
    @Column(name = "security_code")
    @Size(max = 3)
    private String securityCode;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_number")
    private Account account;

    public Card() {
    }
    
    public Card(Card card) {
        this.pan = card.pan;
        this.pin = card.pin;
        this.issueDate = card.issueDate;
        this.expiryDate = card.expiryDate;
        this.cardholderName = card.cardholderName;
        this.securityCode = card.securityCode;
        this.account = card.account;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCardholderName() {
        return cardholderName;
    }

    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "Card{" + "pan=" + pan + ", expiryDate=" + expiryDate + ", cardholderName=" + cardholderName + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + Objects.hashCode(this.pan);
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
}
