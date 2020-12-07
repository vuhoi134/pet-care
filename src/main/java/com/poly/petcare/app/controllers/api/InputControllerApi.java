package com.poly.petcare.app.controllers.api;

import com.poly.petcare.app.dtos.InputDTO;
import com.poly.petcare.app.result.BaseApiResult;
import com.poly.petcare.domain.services.InputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/admin/input")
public class InputControllerApi {

    @Autowired
    private InputService inputService;

    @GetMapping("authen")
    public String checkAuthen(){
        return "SUCCESS";
    }

    @PostMapping("create")
    public BaseApiResult createInput(@RequestBody InputDTO inputDTO){
        return inputService.createInput(inputDTO);
    }
}
