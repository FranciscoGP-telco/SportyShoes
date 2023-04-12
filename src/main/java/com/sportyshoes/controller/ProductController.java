package com.sportyshoes.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
            model.addAttribute("listOfProducts", listOfProducts);
            return "products";
        } else {
            model.addAttribute("loggingNeedError","loggingNeedError");
            model.addAttribute("user", user);
            return "index";
        }
    }

    @GetMapping(value="orderProduct/{productId}")
    public String addProductToCart(Model model, HttpSession httpSession, User user, @PathVariable("productId") int productId){
        List<Product> productsToBuy = new ArrayList<Product>();
        //Checking if the user is logged
        if(httpSession.getAttribute("user") != null){
            //Checking if we have more articles in the list. If not, we create it
            if(httpSession.getAttribute("productsToBuy") == null) {
                httpSession.setAttribute("productsToBuy", productsToBuy);
            } else {
                productsToBuy = (List<Product>)httpSession.getAttribute("productsToBuy");
            }
            //Searching for the product. If we find it, we adding to the shopping Cart variable session
            Product product = productService.getProductById(productId);
            
            if(product != null){
                productsToBuy.add(product);
            }
            System.out.println(productsToBuy.toString());
            return "redirect:/products";
        } else {
            model.addAttribute("loggingNeedError","loggingNeedError");
            model.addAttribute("user", user);
            return "index";
        }
    }
    
    @GetMapping(value="/shopingCart")
    public String shopingCartPage(Model model, HttpSession httpSession, User user){
        //Checking if the user is logged
        if(httpSession.getAttribute("user") != null){
            model.addAttribute("user", user);
            return "shopingcart";
        } else {
            model.addAttribute("loggingNeedError","loggingNeedError");
            model.addAttribute("user", user);
            return "index";
        }
    }
    /*@GetMapping("/admin")
    public String adminPage(Model model, Product product, HttpSession httpSession) {
        
        return "admin";
    }*/
    
}
