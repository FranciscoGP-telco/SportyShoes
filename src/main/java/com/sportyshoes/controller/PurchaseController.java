package com.sportyshoes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sportyshoes.bean.Product;
import com.sportyshoes.bean.Purchase;
import com.sportyshoes.bean.User;
import com.sportyshoes.service.ProductService;
import com.sportyshoes.service.PurchaseService;
import com.sportyshoes.service.UserService;

import jakarta.servlet.http.HttpSession;


@Controller
public class PurchaseController {

    @Autowired
    ProductService productService;

    @Autowired
    PurchaseService purchaseService;
    
    @Autowired
    UserService userService;

    @GetMapping(value="/purchase")
    public String confirmPurchase(Model model, HttpSession httpSession, User user){
        User userlogged = (User)httpSession.getAttribute("user");
        userlogged = userService.getUserByName(userlogged.getUserName());
        //Checking if the user is logged
        if(userlogged != null){
            List<Product> listOfProducts = (List<Product>) httpSession.getAttribute("purchasedProductsCurrently");
            float totalPrice = 0;
            for(Product product : listOfProducts){
                totalPrice += product.getProductPrice();
            }
            model.addAttribute("user", userlogged);
            model.addAttribute("purchasedProductsCurrently", httpSession.getAttribute("purchasedProductsCurrently"));
            model.addAttribute("totalPrice", totalPrice);
            //creating the purchase
            Purchase purchase = new Purchase();
            purchase.setUser(userlogged);
            purchase.setPurchaseproductsProducts(listOfProducts);
            purchaseService.createPurchase(purchase);
            return "purchase";
        } else {
            model.addAttribute("loggingNeedError","loggingNeedError");
            model.addAttribute("user", user);
            return "index";
        }
    }
    
    @GetMapping(value="purchase/{purchaseId}")
    public String searchPurchaseDetails(Model model, HttpSession httpSession, User user, @PathVariable("purchaseId") int purchaseId){
        User userlogged = (User) httpSession.getAttribute("user");
        //Checking if the user is logged
        if(userlogged != null){
            Purchase purchase = purchaseService.getPurchaseById(purchaseId);
            if(purchase != null){
                List<Product> listOfProducts = purchase.getPurchaseproductsProducts();
                User purchaseUser = purchase.getUser();
                float totalPrice = 0;
                for(Product product : listOfProducts){
                    totalPrice += product.getProductPrice();
                }
                model.addAttribute("user", purchaseUser);
                model.addAttribute("purchasedProductsCurrently", listOfProducts);
                model.addAttribute("totalPrice", totalPrice);
                return "purchase";
            } else {
                model.addAttribute("purchaseDoesntFoundError","purchaseDoesntFoundError");
                model.addAttribute("user", user);
                return "index";
            }
        }  else {
            model.addAttribute("loggingNeedError","loggingNeedError");
            model.addAttribute("user", user);
            return "index";
        }
    }

    @GetMapping("/purchaselist")
    public String userList(Model model, User user, HttpSession httpSession){
        User userlogged = (User) httpSession.getAttribute("user");
        //Checking if the user is logged
        if(userlogged != null){
            //Check if the user is admin
            userlogged = userService.getUserByName(userlogged.getUserName());
            if(userlogged.isAdmin()){
                List<Purchase> listOfPurchase = purchaseService.getAllPurchases();
                model.addAttribute("listOfPurchase", listOfPurchase);
                return "listofpurchase";
            } else {
                model.addAttribute("adminNeedError","adminNeedError");
                model.addAttribute("user", user);
                return "index";
            }
        } else {
            model.addAttribute("loggingNeedError","loggingNeedError");
            model.addAttribute("user", user);
            return "index";
        }
    }
    
}
