package com.sportyshoes.bean;

import org.springframework.stereotype.Component;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Component
@Table(name="purchaseproducts")
public class PurchaseProduct {
    private int amount;
    
    @EmbeddedId
    private PurchaseProductKey purchaseProductKey;

    @Embeddable
    public class PurchaseProductKey {
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "purchase_id", nullable = false)
        private Purchase purchase;
    
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "product_id", nullable = false)
        private Product product;
    }

    public Purchase getPurchase() {
        return this.purchaseProductKey.purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchaseProductKey.purchase = purchase;
    }

    public Product getProduct() {
        return this.purchaseProductKey.product;
    }

    public void setProduct(Product product) {
        this.purchaseProductKey.product = product;
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
