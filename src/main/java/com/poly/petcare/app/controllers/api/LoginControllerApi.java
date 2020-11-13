package com.poly.petcare.app.controllers.api;

import com.poly.petcare.app.responses.jwt.LoginRequest;
import com.poly.petcare.app.responses.jwt.LoginResponse;
import com.poly.petcare.domain.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginControllerApi {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/wellcome")
    public String wellcomeController() {
        return "Wellcome to jwt";
    }

    @PostMapping("/authenticate")
    public LoginResponse generatToken(@RequestBody LoginRequest loginRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
        } catch (Exception e) {
            throw new Exception("username và password không hợp lệ");
        }
        String jwt = jwtUtil.generateToken(loginRequest.getUsername());
        return new LoginResponse(jwt);
    }
}
