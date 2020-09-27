package com.poly.petcare.app.controllers;

import com.poly.petcare.app.dtos.UserDTO;
import com.poly.petcare.domain.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/v1/user")
public class UserController{
    @Autowired private UserServices userServices;
    @PostMapping(value = "/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO) {
        return userServices.create(userDTO);
    }
}
