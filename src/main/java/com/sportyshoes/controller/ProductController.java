package com.sportyshoes.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sportyshoes.bean.Product;
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

    //Mapping to the page products. Detects if the users is logged and show the page template products
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

    //Mapping to the page order products. Detects if the users is logged and add the product to the shopping chart
    @GetMapping(value="orderProduct/{productId}")
    public String addProductToCart(Model model, HttpSession httpSession, User user, @PathVariable("productId") int productId){
        User userlogged = (User) httpSession.getAttribute("user");
        //check if a shopping chart exists
        List<Product> purchasedProductsCurrently = (List<Product>) httpSession.getAttribute("purchasedProductsCurrently");
        //Checking if the user is logged
        if(userlogged != null){
            //Checking if we have a purchase created. If not, we create it
            if(purchasedProductsCurrently == null) {
                purchasedProductsCurrently = new ArrayList<Product>();
            }
            //adding the product to the shopping chart
            Product product = productService.getProductById(productId);
            purchasedProductsCurrently.add(product);
            //adding the shopping chart to the session            
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

    //Mapping to the shopping cart to confirm the purchase
    @GetMapping(value="/shopingCart")
    public String shopingCartPage(Model model, HttpSession httpSession, User user){
        User userlogged = (User)httpSession.getAttribute("user");
        userlogged = userService.getUserByName(userlogged.getUserName());
        //Checking if the user is logged
        if(userlogged != null){
            //getting the shopping cart from the session
            List<Product> listOfProducts = (List<Product>) httpSession.getAttribute("purchasedProductsCurrently");
            //Calculating the total ammount of the purchase
            float totalPrice = 0;
            for(Product product : listOfProducts){
                totalPrice += product.getProductPrice();
            }
            //Sending all the values to the model and call the template confirmpurchase
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

    //Mapping to admin the product category
    @GetMapping("/productmanagement")
    public String productManagementPage(Model model, Product product, HttpSession httpSession, User user) {
        User userlogged = (User) httpSession.getAttribute("user");
        //Checking if the user is logged
        if(userlogged != null){
            //Check if the user is admin
            userlogged = userService.getUserByName(userlogged.getUserName());
            if(userlogged.isAdmin()){
                //returning the template productmanagement
                model.addAttribute("listOfProducts", productService.listProducts());
                return "productmanagement";
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

    //Mapping to update the product category
    @PostMapping(value="updateCategory/{productId}")
    public String updateProductCategory(Model model, HttpSession httpSession, User user, @PathVariable("productId") int productId, @RequestParam("category") String category){
        User userlogged = (User) httpSession.getAttribute("user");
        //Checking if the user is logged
        if(userlogged != null){
            //Check if the user is admin
            userlogged = userService.getUserByName(userlogged.getUserName());
            if(userlogged.isAdmin()){
                //Getting the product in the pathvariable id
                Product product = productService.getProductById(productId);
                //Updating the category with the value from the form. After this we come back to productmanagement template
                product.setProductCategory(category);
                productService.storeProduct(product);
                return "redirect:/productmanagement";
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