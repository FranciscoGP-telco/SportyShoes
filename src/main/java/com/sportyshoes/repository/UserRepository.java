package com.sportyshoes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sportyshoes.bean.User;
public interface UserRepository extends JpaRepository <User, Integer> {
    List<User> findByEmail(String email);
    List<User> findByUserName(String userName);
    List<User> findByUserNameIsContaining(String userName);
    
    @Query("select user from User user where user.userName = :userName and user.password = :password")
    User signIn(
        @Param("userName") String userName,
        @Param("password") String password);

}
