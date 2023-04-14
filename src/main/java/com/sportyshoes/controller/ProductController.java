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
import com.sportyshoes.service.UserService;

import jakarta.servlet.http.HttpSession;




@Controller
public class ProductController {
    
    @Autowired
    ProductService productService;

    @Autowired
    PurchaseService purchaseService;

    @Autowired
    PurchasesProductService purchasesProductService;
    
    @Autowired
    UserService userService;

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
        Purchase purchase = null;
        //Checking if the user is logged
        if(httpSession.getAttribute("user") != null){
            //Checking if we have more an purchase created
            if(httpSession.getAttribute("purchase") == null) {
                //if not, create one
                purchase = new Purchase();
                User logedUser = (User)httpSession.getAttribute("user");
                User userToPurchase = userService.getUserByName(logedUser.getUserName());
                purchase.setUser(userToPurchase);
                purchaseService.createPurchase(purchase);
            } else {
                purchase = (Purchase)httpSession.getAttribute("purchase");
            }
            //Searching for the product. If we find it, we adding to the purchase
            Product product = productService.getProductById(productId);
            if(product != null && purchase != null){
                PurchaseProduct purchaseProduct = new PurchaseProduct();
                purchaseProduct.setProduct(product);
                purchaseProduct.setPurchase(purchase);
                System.out.println(purchaseProduct.toString());
                purchasesProductService.addPurchasedProduct(purchaseProduct);
            }
            List <PurchaseProduct> purchasedProductsCurrently = purchasesProductService.listAllProductsOfPurchase(purchase);
            System.out.println(purchasedProductsCurrently);
            model.addAttribute("purchasedProductsCurrently", purchasedProductsCurrently);
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
