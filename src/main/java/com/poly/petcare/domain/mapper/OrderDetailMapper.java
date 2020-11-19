package com.poly.petcare.domain.mapper;

import com.poly.petcare.app.responses.CartProductResponse;
import com.poly.petcare.app.responses.OrderDetailResponse;
import com.poly.petcare.domain.entites.CartProduct;
import com.poly.petcare.domain.entites.OrderDetail;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.modelmapper.ModelMapper;

@Component
public class OrderDetailMapper {
    public OrderDetailResponse convertToDTO(OrderDetail orderDetail){
        System.out.println(orderDetail.getId()+"ở đây");
        ModelMapper modelMapper= new ModelMapper();
        OrderDetailResponse orderDetailResponse =modelMapper.map(orderDetail, OrderDetailResponse.class);
        return orderDetailResponse;
    }
}
