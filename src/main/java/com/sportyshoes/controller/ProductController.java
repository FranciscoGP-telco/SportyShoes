package com.sportyshoes.controller;

import java.util.ArrayList;
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
public class ProductController {
    
    @Autowired
    ProductService productService;

    @Autowired
    PurchaseService purchaseService;
    
    @Autowired
    UserService userService;

    @GetMapping("/products")
    public String listAllProducts(Model model, Product product, HttpSession httpSession, User user) {
        if(httpSession.getAttribute("user") != null){
            
            model.addAttribute("listOfProducts", productService.listProducts());
            return "products";
        } else {
            model.addAttribute("loggingNeedError","loggingNeedError");
            model.addAttribute("user", user);
            return "index";
        }
    }

    @GetMapping(value="orderProduct/{productId}")
    public String addProductToCart(Model model, HttpSession httpSession, User user, @PathVariable("productId") int productId){
        User userlogged = (User) httpSession.getAttribute("user");
        List<Product> purchasedProductsCurrently = (List<Product>) httpSession.getAttribute("purchasedProductsCurrently");
        //Checking if the user is logged
        if(userlogged != null){
            //Checking if we have a purchase created. If not, we create it
            if(purchasedProductsCurrently == null) {
                purchasedProductsCurrently = new ArrayList<Product>();
            }
            Product product = productService.getProductById(productId);
            purchasedProductsCurrently.add(product);
            System.out.println(purchasedProductsCurrently);
            httpSession.setAttribute("purchasedProductsCurrently", purchasedProductsCurrently);
            model.addAttribute("purchasedProductsCurrently", purchasedProductsCurrently);
            model.addAttribute("listOfProducts", productService.listProducts());
            return "products";
        }  else {
            model.addAttribute("loggingNeedError","loggingNeedError");
            model.addAttribute("user", user);
            return "index";
        }
    }

    @GetMapping(value="/shopingCart")
    public String shopingCartPage(Model model, HttpSession httpSession, User user){
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
            return "confirmpurchase";
        } else {
            model.addAttribute("loggingNeedError","loggingNeedError");
            model.addAttribute("user", user);
            return "index";
        }
    }

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
    
}
