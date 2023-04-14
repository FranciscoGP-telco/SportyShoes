package com.sportyshoes.bean;

import org.springframework.stereotype.Component;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Component
@Table(name="purchaseproducts")
public class PurchaseProduct {
    
    @EmbeddedId
    private PurchaseProductKey purchaseProductKey;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("purchaseId")
    @JoinColumn(name = "purchase_id", nullable = false)
    private Purchase purchase;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("productId")
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public PurchaseProductKey getPurchaseProductKey() {
        return this.purchaseProductKey;
    }

    public void setPurchaseProductKey(PurchaseProductKey purchaseProductKey) {
        this.purchaseProductKey = purchaseProductKey;
    }

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

    @Override
    public String toString() {
        return "{" +
            " purchaseProductKey='" + getPurchaseProductKey() + "'" +
            ", purchase='" + getPurchase() + "'" +
            ", product='" + getProduct() + "'" +
            "}";
    }

}
