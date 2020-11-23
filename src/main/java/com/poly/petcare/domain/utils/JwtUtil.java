package com.poly.petcare.domain.utils;

import com.poly.petcare.app.responses.jwt.LoginResponse;
import com.poly.petcare.domain.entites.User;
import com.poly.petcare.domain.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {
    private String secret = "DATN_PETCARE"; // phần chữ ký trong mã Token

    @Autowired
    private UserRepository userRepository;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    } // trích xuất tên người dùng trong mã token và tar về tên người dùng

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) { // trích xuất yêu cầu xác nhận quyền sở hữu token, giải quyết yêu cầu
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) { //trích xuất ngày hết hạn cảu mã token, trả về ngày hết hạn
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    } // kiểm tra ngày hết hạn, trả về thông báo đã hết hạn hay chưa

    public LoginResponse generateToken(String username) { //tạo 1 token dự trên tên người dùng
        Map<String, Object> claims = new HashMap<>();
        User user=userRepository.findByUserName(username);
        String token=createToken(claims, username);
        return new LoginResponse(user.getId(),token);
    }

    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) { // validate mã token
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
