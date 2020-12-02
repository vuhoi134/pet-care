package com.poly.petcare.domain.mapper;

import com.poly.petcare.app.responses.OrderDetailResponse;
import com.poly.petcare.app.responses.OrderResponse;
import com.poly.petcare.domain.entites.Order;
import com.poly.petcare.domain.entites.OrderDetail;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    public OrderResponse convertToDTO(Order order){
        org.modelmapper.ModelMapper modelMapper= new ModelMapper();
        OrderResponse orderResponse =modelMapper.map(order, OrderResponse.class);
        return orderResponse;
    }
}
