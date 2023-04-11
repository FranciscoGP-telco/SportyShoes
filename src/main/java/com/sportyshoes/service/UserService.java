package com.sportyshoes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sportyshoes.bean.User;
import com.sportyshoes.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    UserRepository userRepository;

    public String signUp(User user){
        List<User> resultUser = userRepository.findByEmail(user.getEmail());
        if(resultUser.isEmpty()){
            resultUser = userRepository.findByUserName(user.getUserName());
            if(resultUser.isEmpty()){
                userRepository.save(user);
                return "Account created correctly. Please login in the login webpage";
            } else {
                return "The username already exists. Please choose another one";
            }
        } else {
            return "The email already exists. Please choose another one";
        }
    }

    public String signIn(User user){
        User userFromDb = userRepository.signIn(user.getUserName(), user.getPassword());
        if(userFromDb==null){
            return "Incorrect email or password";
        } else {
            if(userFromDb.isAdmin()){
                return "Admin logged correctly";
            } else {
                return "User logged correctly";
            }
        }
    }
}
