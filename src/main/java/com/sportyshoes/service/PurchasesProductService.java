package com.sportyshoes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sportyshoes.bean.Purchase;
import com.sportyshoes.bean.PurchaseProduct;
import com.sportyshoes.repository.PurchasesProductRepository;

@Service
public class PurchasesProductService {
    
    @Autowired
    PurchasesProductRepository purchasesProductRepository;

    public String addPurchasedProduct(PurchaseProduct purchaseProduct){
        purchasesProductRepository.save(purchaseProduct);
        return "The product was stored in the purchase correctly";
    }

    public List<PurchaseProduct> listAllPurchasedProduct() {
        return purchasesProductRepository.findAll();
    }

    public List<PurchaseProduct> listAllProductsOfPurchase(Purchase purchase){
        return null;
    }

}
