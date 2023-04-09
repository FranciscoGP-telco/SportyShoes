package com.sportyshoes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.sportyshoes.bean.Product;
import com.sportyshoes.service.ProductService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class ProductController {
    
    @Autowired
    ProductService productService;

    @RequestMapping(value="/products", method=RequestMethod.GET)
    public String listAllProducts(Model model, Product product) {
        List<Product> listOfProducts = productService.listProducts();
        model.addAttribute("listOfProducts", listOfProducts);
        return "products";
    }
    
}
