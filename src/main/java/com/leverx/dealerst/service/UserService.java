package com.leverx.dealerst.service;

import com.leverx.dealerst.entity.User;

import java.util.List;

public interface UserService {

    String register(User user, String path);

    void resetPassword(String email, String password);

    void confirm(String token);

    List<User> findAll();

    User findByEmail(String email);

}
