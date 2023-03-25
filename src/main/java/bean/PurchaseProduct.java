package bean;
import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Component
@Table(name="purchaseproducts")
public class PurchaseProduct {
    @Id
    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "purchase_id", nullable = false)
    @Column(name = "purchase_id")
    private Purchase purchase;

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id", nullable = false)
    @Column(name = "product_id")
    private Product product;
    private int amount;
    
    public Purchase getPurchase() {
        return this.purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getAmount() {
        return this.amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
    

    @Override
    public String toString() {
        return "{" +
            " purchase='" + getPurchase() + "'" +
            ", product='" + getProduct() + "'" +
            ", amount='" + getAmount() + "'" +
            "}";
    }

}
