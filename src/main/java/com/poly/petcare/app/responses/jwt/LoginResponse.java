package com.poly.petcare.app.responses.jwt;

import lombok.Data;

@Data
public class LoginResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private Long userId;

    public LoginResponse(Long userId,String accessToken) {
        this.userId=userId;
        this.accessToken = accessToken;
    }
}
