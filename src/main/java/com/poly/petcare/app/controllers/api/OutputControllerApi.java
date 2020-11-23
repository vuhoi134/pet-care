package com.poly.petcare.app.controllers.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/admin/output")
public class OutputControllerApi {

    @GetMapping("authen")
    public String checkAuthen(){
        return "Check Authen SUCCESS";
    }
}
