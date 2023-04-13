package com.sportyshoes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sportyshoes.bean.Product;
import com.sportyshoes.bean.Purchase;
import com.sportyshoes.bean.PurchaseProduct;
import com.sportyshoes.bean.User;
import com.sportyshoes.service.ProductService;
import com.sportyshoes.service.PurchaseService;
import com.sportyshoes.service.PurchasesProductService;

import jakarta.servlet.http.HttpSession;




@Controller
public class ProductController {
    
    @Autowired
    ProductService productService;

    @Autowired
    PurchaseService purchaseService;

    @Autowired
    PurchasesProductService purchasesProductService;
    
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
    public String addProductToCart(Model model, HttpSession httpSession, User user, @PathVariable("productId") int productId, PurchaseProduct purchaseProduct){
        Purchase purchase = null;
        //Checking if the user is logged
        if(httpSession.getAttribute("user") != null){
            //Checking if we have more an purchase created
            if(httpSession.getAttribute("purchase") == null) {
                //if not, create one
                purchase = new Purchase();
                User logedUser = (User)httpSession.getAttribute("user");
                purchase.setUser(logedUser);
                purchaseService.createPurchase(purchase);
            } else {
                purchase = (Purchase)httpSession.getAttribute("purchase");
            }
            //Searching for the product. If we find it, we adding to the purchase
            Product product = productService.getProductById(productId);
            if(product != null && purchase != null){
                purchasesProductService.addPurchasedProduct(purchase, product, purchaseProduct);
            }
            //System.out.println(productsToBuy.toString());
            return "redirect:/products";
        } else {
            model.addAttribute("loggingNeedError","loggingNeedError");
            model.addAttribute("user", user);
            //Send also purchasesProducts and print all.
            return "index";
        }
    }
    
    @GetMapping(value="/shopingCart")
    public String shopingCartPage(Model model, HttpSession httpSession, User user){
        //Checking if the user is logged
        if(httpSession.getAttribute("user") != null){
            model.addAttribute("user", user);
            model.addAttribute("productsToBuy", httpSession.getAttribute("productsToBuy"));
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
