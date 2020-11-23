package com.poly.petcare.domain.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.poly.petcare.app.dtos.UserResDTO;
import com.poly.petcare.domain.entites.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CustomUserDetails implements UserDetails {

//    User user;

    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String password;

    private List<GrantedAuthority> authorities;

    public CustomUserDetails (UserResDTO user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.authorities = user.getRole().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
