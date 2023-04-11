package com.sportyshoes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.sportyshoes.bean.Product;
import com.sportyshoes.bean.User;
import com.sportyshoes.service.ProductService;

import jakarta.servlet.http.HttpSession;


@Controller
public class ProductController {
    
    @Autowired
    ProductService productService;

    @GetMapping("/products")
    public String listAllProducts(Model model, Product product, HttpSession httpSession, User user) {
        if(httpSession.getAttribute("user") != null){
            List<Product> listOfProducts = productService.listProducts();
            model.addAttribute("listOfProucts", listOfProducts);
            return "products";
        } else {
            model.addAttribute("loggingNeedError","loggingNeedError");
            model.addAttribute("user", user);
            return "index";
        }
    }

    @GetMapping("/admin")
    public String adminPage(Model model, Product product, HttpSession httpSession) {
        
        return "admin";
    }
    
}
