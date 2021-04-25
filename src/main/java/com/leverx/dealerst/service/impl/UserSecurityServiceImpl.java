package com.leverx.dealerst.service.impl;

import com.leverx.dealerst.entity.CustomUserDetails;
import com.leverx.dealerst.entity.User;
import com.leverx.dealerst.repository.UserRepository;
import com.leverx.dealerst.service.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityServiceImpl implements UserSecurityService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(s);
        return CustomUserDetails.fromUserEntityToCustomUserDetails(user);
    }
}
