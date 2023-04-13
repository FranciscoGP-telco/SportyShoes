package com.sportyshoes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.sportyshoes.bean.User;
import com.sportyshoes.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
    
    @Autowired
    UserService userService;

    @GetMapping("/")
    public String mainPage(Model model, User user){
        model.addAttribute("user", user);
        return "index";
    }

    @GetMapping("/signup")
    public String signUpPage(Model model, User user){
        model.addAttribute("user", user);
        return "signup";
    }

    @PostMapping("/signup")
    public String signUp(Model model, User user){
        String signUpResult = userService.signUp(user);
        model.addAttribute("user", user);
        System.out.println(signUpResult);
        return "index";
    } 

    @PostMapping("/")
    public String signIn(Model model, User user, HttpSession httpSession){
        String result = userService.signIn(user);
        if (result.equals("Admin logged correctly")) {
            httpSession.setAttribute("user", user);
            return "adminPage";
        } else if (result.equals("User logged correctly")) {
            httpSession.setAttribute("user", user);
            return "redirect:/products";
        } else {
            model.addAttribute("loggingError","loggingError");
            return "index";
        }
    }
}
