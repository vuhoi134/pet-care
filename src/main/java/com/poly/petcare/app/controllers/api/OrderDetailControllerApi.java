package com.poly.petcare.app.controllers.api;

import com.poly.petcare.app.dtos.OrderDetailDTO;
import com.poly.petcare.app.result.BaseApiResult;
import com.poly.petcare.app.result.DataApiResult;
import com.poly.petcare.domain.services.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/orderDetail")
public class OrderDetailControllerApi {

    @Autowired
    private OrderDetailService orderDetailService;

    @PostMapping("create/{oDDId}")
    public DataApiResult createODD(@PathVariable Long oDDId,
            @RequestBody OrderDetailDTO dto){
        return orderDetailService.newOrderDetail(oDDId,dto);
    }
    @DeleteMapping("delete/{oDDId}")
    public BaseApiResult delete(@PathVariable Long oDDId) {
        return orderDetailService.deleteODD(oDDId);
    }
}
