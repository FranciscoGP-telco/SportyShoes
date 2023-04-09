package com.sportyshoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sportyshoes.bean.PurchaseProduct;

@Repository
public interface PurchasesProductRepository extends JpaRepository <PurchaseProduct, Integer>{
    
}
