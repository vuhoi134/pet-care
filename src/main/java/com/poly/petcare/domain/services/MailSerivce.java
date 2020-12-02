package com.poly.petcare.domain.services;

import com.poly.petcare.app.controllers.web.MyConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;

@Controller
public class MailSerivce {
    @Autowired
    public JavaMailSender emailSender;

    public Boolean sendSimpleEmail(String email,String subject,String text) {
        try {
            // Create a Simple MailMessage.
            SimpleMailMessage message = new SimpleMailMessage();

            message.setTo(email);
            message.setSubject(subject);
            message.setText(text);

            // Send Message!
            this.emailSender.send(message);

            return true;
        }catch(Exception e){
            return true;
        }
    }
}
