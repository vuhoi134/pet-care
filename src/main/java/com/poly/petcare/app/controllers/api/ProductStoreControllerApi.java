package com.poly.petcare.app.controllers.api;

import com.poly.petcare.app.result.DataApiResult;
import com.poly.petcare.domain.services.ProductStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/v1/productStore")
public class ProductStoreControllerApi {
    @Autowired
    private ProductStoreService productStoreService;

    @GetMapping(value = "/listProductStore")
    public DataApiResult listProductStore(@RequestParam(name = "page") Optional<Integer> page,
                                          @RequestParam(name = "limit") Optional<Integer> limit,
                                          @RequestParam(name = "productName") String productName){
        return productStoreService.listProductStore(page.orElse(0),limit.orElse(0),productName);
    }
}
