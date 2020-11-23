package com.poly.petcare.domain.services;

import com.poly.petcare.app.dtos.UserResDTO;
import com.poly.petcare.domain.entites.Role;
import com.poly.petcare.domain.entites.User;
import com.poly.petcare.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserResDTO user = new UserResDTO(userRepository.findByUserName(userName));
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
            return new CustomUserDetails(user);
//        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
//        Set<Role> roles = user.getRoles();
//        for (Role role : roles) {
//            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
//        }
//
//        return new org.springframework.security.core.userdetails.User(
//                user.getUserName(), user.getPassWord(), grantedAuthorities);
    }
}
