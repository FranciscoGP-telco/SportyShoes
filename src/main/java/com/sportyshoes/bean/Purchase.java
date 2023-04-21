package com.sportyshoes.bean;

import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Component
@Table(name="purchases")
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_id")
    private int purchaseId;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToMany
    @JoinTable(
            name = "purchaseproducts",
            joinColumns = @JoinColumn(name = "purchase_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> purchaseproductsProducts;


    public int getPurchaseId() {
        return this.purchaseId;
    }

    public void setPurchaseId(int purchaseId) {
        this.purchaseId = purchaseId;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Product> getPurchaseproductsProducts() {
        return this.purchaseproductsProducts;
    }

    public void setPurchaseproductsProducts(List<Product> purchaseproductsProducts) {
        this.purchaseproductsProducts = purchaseproductsProducts;
    }
    

    @Override
    public String toString() {
        return "{" +
            " purchaseId='" + getPurchaseId() + "'" +
            ", user='" + getUser() + "'" +
            ", purchaseproductsProducts='" + getPurchaseproductsProducts() + "'" +
            "}";
    }

}
