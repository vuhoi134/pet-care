package com.poly.petcare.app.controllers.api;

import com.poly.petcare.domain.services.UserRoleServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/user")
public class UserRoleControllerAPI {
    @Autowired
    UserRoleServices userRoleServices;
//    @GetMapping(value = "/search/{id}")
//    private ResponseEntity<?> search(@PathVariable Long id){
//        return userRoleServices.searchByRoleID(id);
//    }
}
