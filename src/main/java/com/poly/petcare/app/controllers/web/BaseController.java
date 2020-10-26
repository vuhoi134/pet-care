package com.poly.petcare.app.controllers.web;

import com.poly.petcare.domain.entites.Cart;
import com.poly.petcare.domain.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Objects;
import java.util.UUID;

public class BaseController {
    @Autowired
    private CartRepository cartRepository;

    public void checkCookie(HttpServletResponse response, HttpServletRequest request, final Principal principal) {
        Cookie cookie[] = request.getCookies();
        if (principal != null) {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            Cart cartEntity = cartRepository.findByUserName(username);
            if (Objects.isNull(cartEntity)) {
                Cookie cookie1 = new Cookie("guid", cartEntity.getGuid());
                cookie1.setPath("/");
                cookie1.setMaxAge(60 * 60 * 24);
                response.addCookie(cookie1);
            } else {
                UUID uuid = UUID.randomUUID();
                String guid = uuid.toString();
                Cart cart = new Cart();
                cart.setGuid(guid);
                cart.setUserName(username);
                cartRepository.save(cart);

                Cookie cookie2 = new Cookie("guid", guid);
                cookie2.setPath("/");
                cookie2.setMaxAge(60 * 60 * 24);
                response.addCookie(cookie2);

            }
        } else {
            boolean flag2 = true;

            String guid = null;

            if (cookie != null) {
                for (Cookie c : cookie) {
                    if (c.getName().equals("guid")) {
                        flag2 = false;
                        guid = c.getValue();
                    }
                }
            }
            if (flag2 == true) {
                UUID uuid = UUID.randomUUID();
                String guid2 = uuid.toString();
                Cart cart2 = new Cart();
                cart2.setGuid(guid2);
                cartRepository.save(cart2);

                Cookie cookie3 = new Cookie("guid", guid2);
                cookie3.setPath("/");
                cookie3.setMaxAge(60 * 60 * 24);
                response.addCookie(cookie3);

            } else {

                Cart cartEntity = cartRepository.findFirstCartByGuid(guid);
                if (Objects.nonNull(cartEntity)) {
                    Cart cart3 = new Cart();
                    cart3.setGuid(guid);
                    cartRepository.save(cart3);
                }
            }
        }
    }
}

