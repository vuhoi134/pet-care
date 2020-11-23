package com.poly.petcare.app.controllers.api;

import com.poly.petcare.app.dtos.auth.AuthResCheckLogin;
import com.poly.petcare.app.responses.jwt.LoginRequest;
import com.poly.petcare.app.responses.jwt.LoginResponse;
import com.poly.petcare.domain.services.AuthService;
import com.poly.petcare.domain.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/login")
@CrossOrigin
public class LoginControllerApi {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthService authService;

    @GetMapping("/wellcome")
    public String wellcomeController() {
        return "Wellcome to jwt";
    }

    @PostMapping("/authenticate")
    public LoginResponse generateToken(@RequestBody LoginRequest loginRequest) throws Exception {
        try {
            Authentication authentication =authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            throw new Exception("username và password không hợp lệ");
        }
        return jwtUtil.generateToken(loginRequest.getUsername());
    }

    @PostMapping("/checkLogin")
    public AuthResCheckLogin checkAuth(){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        return authService.getRole(authentication.getName());
    }
}
