package com.poly.petcare.app.dtos.auth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collection;

@Getter
@Setter
public class AuthResCheckLogin {
    private Long userID;
    private Collection<? extends GrantedAuthority> authorities;
}
