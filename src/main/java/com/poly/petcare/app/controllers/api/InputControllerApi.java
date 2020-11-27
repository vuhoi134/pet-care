package com.poly.petcare.app.controllers.api;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/admin/input")
public class InputControllerApi {

    @GetMapping("authen")
    public String checkAuthen(){
        return "SUCCESS";
    }
}
