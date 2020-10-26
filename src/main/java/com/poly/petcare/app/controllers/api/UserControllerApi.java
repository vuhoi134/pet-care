package com.poly.petcare.app.controllers.api;

import com.poly.petcare.app.dtos.UserDTO;
import com.poly.petcare.domain.services.UserRoleServices;
import com.poly.petcare.domain.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
@RestController
@RequestMapping(value = "/v1/user")
public class UserControllerApi {
    @Autowired
    private UserServices userServices;
    @Autowired
    UserRoleServices userRoleServices;
    @PostMapping(value = "/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO) {
        return userServices.create(userDTO);
    }

}
