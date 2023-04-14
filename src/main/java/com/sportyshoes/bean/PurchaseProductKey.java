package com.sportyshoes.bean;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class PurchaseProductKey implements Serializable {
    
    @Column(name = "purchase_id")
    private int purchaseId;

    @Column(name = "product_id")
    private int productId;


    public int getPurchaseId() {
        return this.purchaseId;
    }

    public void setPurchaseId(int purchaseId) {
        this.purchaseId = purchaseId;
    }

    public int getProductId() {
        return this.productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "{" +
            " purchaseId='" + getPurchaseId() + "'" +
            ", productId='" + getProductId() + "'" +
            "}";
    }

}
