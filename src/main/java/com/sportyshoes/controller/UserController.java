package com.sportyshoes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @PostMapping("/")
    public String signIn(Model model, User user, HttpSession httpSession){
        String result = userService.signIn(user);
        if (result.equals("Admin logged correctly")) {
            httpSession.setAttribute("user", user);
            return "admin";
        } else if (result.equals("User logged correctly")) {
            httpSession.setAttribute("user", user);
            return "redirect:/products";
        } else {
            model.addAttribute("loggingError","loggingError");
            return "index";
        }
    }

    @GetMapping("/admin")
    public String adminPage(Model model, User user, HttpSession httpSession){
        User userlogged = (User) httpSession.getAttribute("user");
        //Checking if the user is logged
        if(userlogged != null){
            //Check if the user is admin
            userlogged = userService.getUserByName(userlogged.getUserName());
            if(userlogged.isAdmin()){
                model.addAttribute("user", user);
                return "admin";
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

    @GetMapping("/signup")
    public String signUpPage(Model model, User user){
        model.addAttribute("user", user);
        return "signup";
    }

    @PostMapping("/signup")
    public String signUp(Model model, User user){
        String signUpResult = userService.signUp(user);
        System.out.println(signUpResult);
        model.addAttribute("user", user);
        return "index";
    } 

    @GetMapping("/userlist")
    public String userList(Model model, User user, HttpSession httpSession){
        User userlogged = (User) httpSession.getAttribute("user");
        //Checking if the user is logged
        if(userlogged != null){
            //Check if the user is admin
            userlogged = userService.getUserByName(userlogged.getUserName());
            if(userlogged.isAdmin()){
                List<User> userList = userService.getAllUsers();
                model.addAttribute("userList", userList);
                return "listofusers";
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


    @GetMapping("/searchuser")
    public String searchUserPage(Model model, User user, HttpSession httpSession){
        User userlogged = (User) httpSession.getAttribute("user");
        //Checking if the user is logged
        if(userlogged != null){
            //Check if the user is admin
            userlogged = userService.getUserByName(userlogged.getUserName());
            if(userlogged.isAdmin()){
                model.addAttribute("user", user);
                return "searchuser";
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

    @PostMapping("/searchuser")
    public String searchUserPostPage(Model model, User user, HttpSession httpSession, @RequestParam("userName") String userName){
        User userlogged = (User) httpSession.getAttribute("user");
        //Checking if the user is logged
        if(userlogged != null){
            //Check if the user is admin
            userlogged = userService.getUserByName(userlogged.getUserName());
            if(userlogged.isAdmin()){
                List<User> listOfUsers = userService.getUsersFindByName(userName);
                model.addAttribute("listOfUsers", listOfUsers);
                return "searchuser";
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
