package com.sportyshoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sportyshoes.bean.PurchaseProduct;
import com.sportyshoes.bean.PurchaseProductKey;

public interface PurchasesProductRepository extends JpaRepository <PurchaseProduct, PurchaseProductKey>{

}
