package com.sportyshoes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sportyshoes.bean.Product;
import com.sportyshoes.repository.ProductRepository;

@Service
public class ProductService {
    
    @Autowired
    ProductRepository productRepository;

    public String storeProduct(Product product){
        productRepository.save(product);
        return "Product stored correctly";
    }

    public List<Product> listProducts(){
        return productRepository.findAll();
    }

    public void changeCategory(int productId, String category){
        Optional<Product> productResult = productRepository.findById(productId);
        if(productResult.isPresent()){
            Product product = productResult.get();
            product.setProductCategory(category);
            productRepository.saveAndFlush(product);
        }
    }

    public Product getProductById(int productId){
        return productRepository.getReferenceById(productId);
    }

    
}
