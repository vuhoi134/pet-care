package com.poly.petcare.app.controllers.api;

import com.poly.petcare.app.dtos.InputDTO;
import com.poly.petcare.app.result.BaseApiResult;
import com.poly.petcare.app.result.DataApiResult;
import com.poly.petcare.domain.services.InputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

    @GetMapping("list")
    public DataApiResult listInput(@RequestParam(name = "page") Optional<Integer> page,
                                   @RequestParam(name = "limit") Optional<Integer> limit,
                                   @RequestParam(name = "name") String name,
                                   @RequestParam(name = "status") Optional<Integer> status){
        return inputService.listInput(page.orElse(0),limit.orElse(0),name,status.orElse(0));
    }
}
