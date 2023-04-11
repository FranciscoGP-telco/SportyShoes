package com.sportyshoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sportyshoes.bean.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    
}
