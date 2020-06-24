package rs.grgur.jovan.optika.model;

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
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "transactions")
public class Transaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "timestamp")
    @NotNull
    private double debit;
    
    @Column(name = "amount")
    @NotNull
    private double credit;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_number")
    private Account account;

    public Transaction() {
    }
    
    public Transaction(Transaction transaction) {
        this.id = transaction.id;
        this.debit = transaction.debit;
        this.credit = transaction.credit;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getDebit() {
        return debit;
    }

    public void setDebit(double debit) {
        this.debit = debit;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public Account getAccountNumber() {
        return account;
    }

    public void setAccountNumber(Account accountNumber) {
        this.account = accountNumber;
    }

    @Override
    public String toString() {
        return "Transaction{" + "id=" + id + ", accountNumber=" + account + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + (int) (this.id ^ (this.id >>> 32));
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
        final Transaction other = (Transaction) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
}
