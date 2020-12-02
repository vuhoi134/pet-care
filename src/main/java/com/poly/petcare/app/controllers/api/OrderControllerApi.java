package com.poly.petcare.app.controllers.api;

import com.poly.petcare.app.dtos.OrderDTO;
import com.poly.petcare.app.result.BaseApiResult;
import com.poly.petcare.app.result.DataApiResult;
import com.poly.petcare.domain.services.OrderService;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping(value = "/v1/order")
public class OrderControllerApi {

    @Autowired
    private OrderService orderService;

    @PostMapping("create")
    public BaseApiResult createOrder(@Valid @RequestBody OrderDTO orderDTO){
        return orderService.createOrder(orderDTO);
    }

    @PostMapping("createOrderStore")
    public BaseApiResult createOrderStore(@Valid @RequestBody OrderDTO orderDTO){
        return orderService.createOrderStore(orderDTO);
    }

    @GetMapping("listOrder")
    public DataApiResult listOrderDetail(@RequestParam(name = "guid") String guid,
                                         @RequestParam(name = "userId") Long userId){
        return orderService.getListOrder(guid, userId);
    }

    @GetMapping("allListOrder")
    public DataApiResult allListOrder(@RequestParam(name = "page") Optional<Integer> page,
                                      @RequestParam(name = "limit") Optional<Integer> limit,
                                      @RequestParam(name = "status") Optional<Integer> status){
        return orderService.getAllListOrder(page.orElse(0),limit.orElse(0),status.orElse(0));
    }

    @GetMapping("orderDetail/{orderId}")
    public DataApiResult orderDetail(@PathVariable Long orderId){
        return orderService.orderDetail(orderId);
    }

    @PutMapping("updateOrderStore/{orderId}")
    public BaseApiResult updateOrderStore(@PathVariable Long orderId,
            @Valid @RequestBody OrderDTO orderDTO){
        return orderService.updateOrderStore(orderId,orderDTO);
    }

    @PutMapping("updateOrderDetail")
    public BaseApiResult updateOrderDetail(@RequestParam(name = "orderDetailId") Long orderDetailId,
                                           @RequestParam(name = "quantity") Integer quantity){
        return orderService.updateODD(orderDetailId,quantity);
    }

    @PutMapping("updateStatusOrder")
    public BaseApiResult updateStatusOrder(@RequestParam(name = "orderId") Long orderId,
                                           @RequestParam(name = "status") Integer status){
        return orderService.updateStatusODD(orderId,status);
    }
}
