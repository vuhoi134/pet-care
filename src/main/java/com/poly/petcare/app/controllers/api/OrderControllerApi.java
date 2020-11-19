package com.poly.petcare.app.controllers.api;

import com.poly.petcare.app.dtos.OrderDTO;
import com.poly.petcare.app.result.BaseApiResult;
import com.poly.petcare.app.result.DataApiResult;
import com.poly.petcare.domain.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/v1/order")
@CrossOrigin
public class OrderControllerApi {

    @Autowired
    private OrderService orderService;

    @PostMapping("create")
    public BaseApiResult createOrder(@Valid @RequestBody OrderDTO orderDTO){
        return orderService.createOrder(orderDTO);
    }

    @GetMapping("listOrder")
    public DataApiResult listOrderDetail(@RequestParam(name = "guid") String guid,
                                         @RequestParam(name = "userId") Long userId){
        return orderService.getListOrder(guid, userId);
    }
}
