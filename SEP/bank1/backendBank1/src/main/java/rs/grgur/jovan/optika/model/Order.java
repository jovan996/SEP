package rs.grgur.jovan.optika.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
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
import javax.validation.constraints.Size;

@Entity
@Table(name = "orders")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private long orderId;

    @Column(name = "timestamp")
    @NotNull
    private Date timestamp;
    
    @Column(name = "amount")
    @NotNull
    private double amount;

    @Column(name = "success_url")
    @Size(max = 100)
    private String successUrl;
    
    @Column(name = "failed_url")
    @Size(max = 100)
    private String failedUrl;
    
    @Column(name = "error_url")
    @Size(max = 100)
    private String errorUrl;
    
    @Column(name = "payment_url")
    @Size(max = 100)
    private String paymentUrl;
    
    @Column(name = "paid")
    private boolean paid;
    
    @Column(name = "merchant_order_id")
    private int merchantOrderId;
    
    @Column(name = "acquirer_timestamp")
    private Date acquirerTimestamp;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "businesses_merchant_id")
    private Business business;

    public Order() {
    }
    
    public Order(Order order) {
        this.orderId = order.orderId;
        this.amount = order.amount;
        this.successUrl = order.successUrl;
        this.failedUrl = order.failedUrl;
        this.errorUrl = order.errorUrl;
        this.paymentUrl = order.paymentUrl;
        this.business = order.business;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getSuccessUrl() {
        return successUrl;
    }

    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }

    public String getFailedUrl() {
        return failedUrl;
    }

    public void setFailedUrl(String failedUrl) {
        this.failedUrl = failedUrl;
    }

    public String getErrorUrl() {
        return errorUrl;
    }

    public void setErrorUrl(String errorUrl) {
        this.errorUrl = errorUrl;
    }

    public String getPaymentUrl() {
        return paymentUrl;
    }

    public void setPaymentUrl(String paymentUrl) {
        this.paymentUrl = paymentUrl;
    }

    public boolean getPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public int getMerchantOrderId() {
        return merchantOrderId;
    }

    public void setMerchantOrderId(int merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
    }

    public Date getAcquirerTimestamp() {
        return acquirerTimestamp;
    }

    public void setAcquirerTimestamp(Date acquirerTimestamp) {
        this.acquirerTimestamp = acquirerTimestamp;
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    @Override
    public String toString() {
        return "Order{" + "orderId=" + orderId + ", timestamp=" + timestamp + ", amount=" + amount + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.orderId);
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
        final Order other = (Order) obj;
        if (!Objects.equals(this.orderId, other.orderId)) {
            return false;
        }
        return true;
    }
}
