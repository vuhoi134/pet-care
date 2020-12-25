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

    @PostMapping(value = "delete/{userID}")
    public ResponseEntity<?> delete(@PathVariable Long userID){
        return userServices.delete(userID);
    }

    @PutMapping(value = "edit/{userID}")
    public ResponseEntity<?> edit(@PathVariable Long userID,@RequestParam(name = "status") Boolean status){
        return userServices.edit(userID,status);
    }

    @PostMapping(value = "forgot")
    public ResponseEntity<?> forgotPassword(@RequestParam(name = "forgot") String forgot){
        return userServices.forgotPassword(forgot);
    }

}
