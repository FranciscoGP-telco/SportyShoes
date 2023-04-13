package com.sportyshoes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sportyshoes.bean.Purchase;
import com.sportyshoes.repository.PurchasesRepository;

@Service
public class PurchaseService {
    
    @Autowired
    PurchasesRepository purchasesRepository;

    public String createPurchase(Purchase purchase){
        purchasesRepository.save(purchase);
        return "Purchase created correctly";
    }

    public Purchase getPurchaseById(Integer purchaseId){
        return purchasesRepository.getReferenceById(purchaseId);
    }

    public List<Purchase> getAllPurchases(){
        return purchasesRepository.findAll();
    }
}
