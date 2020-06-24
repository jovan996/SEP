package rs.grgur.jovan.optika.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "businesses")
public class Business implements Serializable {

    @Id
    @Column(name = "merchant_id", unique = true)
    @NotNull
    @Size(max = 30)
    private String merchantId;

    @Column(name = "merchant_password")
    @NotNull
    @Size(max = 100)
    private String merchantPassword;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "businesses_merchant_id")
    @JsonIgnore
    private Set<Order> orders;
    
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "businesses_merchant_id")
    @JsonIgnore
    private Set<Account> accounts;

    public Business() {
    }

    public Business(Business business) {
        this.merchantId = business.merchantId;
        this.merchantPassword = business.merchantPassword;
        this.orders = business.orders;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantPassword() {
        return merchantPassword;
    }

    public void setMerchantPassword(String merchantPassword) {
        this.merchantPassword = merchantPassword;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        return "Business{" + "merchantId=" + merchantId + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.merchantId);
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
        final Business other = (Business) obj;
        if (!Objects.equals(this.merchantId, other.merchantId)) {
            return false;
        }
        return true;
    }
}
