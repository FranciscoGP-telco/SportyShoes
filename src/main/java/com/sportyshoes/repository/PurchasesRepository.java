package com.sportyshoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sportyshoes.bean.Purchase;

public interface PurchasesRepository extends JpaRepository<Purchase, Integer>  {
    
}
