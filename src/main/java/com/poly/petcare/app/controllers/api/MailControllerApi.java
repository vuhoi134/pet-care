package com.poly.petcare.app.controllers.api;

import com.poly.petcare.domain.services.MailSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/sentmail")
public class MailControllerApi {
//    @Autowired
//    private MailSerivce mailSerivce;
//    @GetMapping
//    public String sentMail(@RequestParam(name = "email") String email){
//        return mailSerivce.sendSimpleEmail(email);
//    }
}
