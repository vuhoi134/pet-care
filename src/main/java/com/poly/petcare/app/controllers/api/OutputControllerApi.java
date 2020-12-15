package com.poly.petcare.app.controllers.api;

import com.poly.petcare.app.dtos.InputDTO;
import com.poly.petcare.app.dtos.OutputDTO;
import com.poly.petcare.app.result.BaseApiResult;
import com.poly.petcare.domain.services.InputService;
import com.poly.petcare.domain.services.OutputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/admin/output")
public class OutputControllerApi {

    @Autowired
    private OutputService outputService;

    @GetMapping("authen")
    public String checkAuthen(){
        return "Check Authen SUCCESS";
    }

    @PostMapping("create")
    public BaseApiResult createOutput(@RequestBody OutputDTO outputDTO){
        return outputService.createOutput(outputDTO);
    }
}
