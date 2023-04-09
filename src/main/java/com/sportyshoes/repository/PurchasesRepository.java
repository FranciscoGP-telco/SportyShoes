package com.sportyshoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sportyshoes.bean.Purchase;

@Repository
public interface PurchasesRepository extends JpaRepository<Purchase, Integer>  {
    
}
