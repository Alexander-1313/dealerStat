package com.leverx.dealerst.controller;

import com.leverx.dealerst.config.jwt.JwtFilter;
import com.leverx.dealerst.config.jwt.JwtUtils;
import com.leverx.dealerst.entity.User;
import com.leverx.dealerst.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
public class RegistrationController {

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtFilter jwtFilter;

    @GetMapping("/register")
    public String register(@RequestBody User user, HttpServletRequest request){
        userService.register(user, request.getServletPath());

        return jwtUtils.generateToken(user.getEmail());//каждый раз генерит новый токен TODO
    }

    @GetMapping("/register/confirm")
    public String confirm(@RequestParam("token") String token){
        userService.confirm(token);
        return token;
    }

    @PostMapping("/auth/reset")
    public String resetPassword(@RequestParam("token") String token,
                                    HttpServletRequest request){
        userService.confirm(token);
        return "password was reseted!";
    }

    @PostMapping("/auth/forgot_password")
    public String forgotPassword(@RequestParam("email") String email,
                                 @RequestParam("password") String password){
        userService.resetPassword(email, password);
        return "forgot password!";
    }

    @GetMapping("/auth/check_code")
    public String checkCode(@RequestParam("code") String code){
        if(jwtUtils.validateToken(code)){
            return code;
        }
        return "this code is not valid";
    }

    @GetMapping("/auth")
    public String auth(@RequestBody User user){
        String token = jwtUtils.generateToken(user.getEmail());
        return token;
    }

    @GetMapping("/isauth")
    public String isAuth(@RequestBody User user,
                         Principal principal){
        System.out.println(principal.getName());
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
