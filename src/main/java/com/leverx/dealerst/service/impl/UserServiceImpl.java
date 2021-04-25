package com.leverx.dealerst.service.impl;

import com.leverx.dealerst.config.jwt.JwtUtils;
import com.leverx.dealerst.entity.User;
import com.leverx.dealerst.entity.UserRole;
import com.leverx.dealerst.repository.UserRepository;
import com.leverx.dealerst.service.MailService;
import com.leverx.dealerst.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);
    private UserRepository userRepository;
    private MailService mailService;
    private JwtUtils jwtUtils;
    private Jedis jedis;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, MailService mailService, JwtUtils jwtUtils, Jedis jedis) {
        this.userRepository = userRepository;
        this.mailService = mailService;
        this.jwtUtils = jwtUtils;
        this.jedis = jedis;
    }

    @Override
    public String register(User user, String path) {
        String token = jwtUtils.generateToken(user.getEmail());
        jedis.set(token, user.getEmail());

        mailService.sendEmail(user.getEmail(), "REGISTRATION", "http://localhost:8080" + path + "/confirm?token=" + token);

        user.setCreatedAt(new Date());
        user.setUserRole(UserRole.TRADER);
        userRepository.save(user);
        LOGGER.info("user with email " + user.getEmail() + " was added to DB");
        return token;
    }

    @Override
    public void resetPassword(String email, String password) {
        String token = jwtUtils.generateToken(email);
        jedis.set(token, email);
        mailService.sendEmail(email, "RESET PASSWORD", "http://localhost:8080/auth/reset?token=" + token + "&password=" + password);
    }

//    public void confirmResetPassword(String token){
//        if(jedis.exists(token) && jwtUtils.validateToken(token)){
//            String email = jedis.get(token);
//            User userByEmail = userRepository.findByEmail(email);
//            userByEmail.setEnabled(true);
//            userRepository.save(userByEmail);
//            System.out.println("User with email " + email + " is confirmed");
//        }else {
//            System.out.println("There is not such a token");
//        }
//    }

    @Override
    public void confirm(String token) {
        if(jedis.exists(token) && jwtUtils.validateToken(token)){
           String email = jedis.get(token);
            User userByEmail = userRepository.findByEmail(email);
            System.out.println(userByEmail);
            userByEmail.setEnabled(true);
            userRepository.save(userByEmail);
            LOGGER.info("User with email " + email + " is confirmed");
        }else {
            LOGGER.info("There is not such a token");
        }
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
