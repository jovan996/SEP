package rs.grgur.jovan.optika.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "account")
public class Account implements Serializable {

    @Id
    @Column(name = "number", unique = true)
    @Size(max = 10)
    private String number;

    @Column(name = "balance")
    private double balance;

    @Column(name = "reserved")
    private double reserved;

    public Account() {
    }
    
    public Account(Account account) {
        this.number = account.number;
        this.balance = account.balance;
        this.reserved = account.reserved;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getReserved() {
        return reserved;
    }

    public void setReserved(double reserved) {
        this.reserved = reserved;
    }

    @Override
    public String toString() {
        return "Account{" + "number=" + number + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.number);
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
        final Account other = (Account) obj;
        if (!Objects.equals(this.number, other.number)) {
            return false;
        }
        return true;
    }
}
