package com.poly.petcare.domain.services;

import com.poly.petcare.app.dtos.UserDTO;
import com.poly.petcare.app.dtos.auth.AuthResCheckLogin;
import com.poly.petcare.domain.entites.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService extends BaseServices{

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    public AuthResCheckLogin getRole(String username) {
        AuthResCheckLogin auth = new AuthResCheckLogin();
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        User user = userRepository.findByUserName(username);
        if (user != null) {
            auth.setUserID(user.getId());
        } else {
            auth.setUserID(0L);
        }
        auth.setAuthorities(userDetails.getAuthorities());
        return auth;
    }
}
