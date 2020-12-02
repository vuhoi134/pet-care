package com.poly.petcare.domain.services;

import com.poly.petcare.app.dtos.OrderDetailDTO;
import com.poly.petcare.app.result.DataApiResult;
import com.poly.petcare.domain.entites.Order;
import com.poly.petcare.domain.entites.OrderDetail;
import com.poly.petcare.domain.entites.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderDetailService extends BaseServices{

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public DataApiResult newOrderDetail(Long orderId,OrderDetailDTO detailDTO){
        DataApiResult result =new DataApiResult();
        Product product=productRepository.getOne(detailDTO.getProductId());
        Order order=orderRepository.getOne(orderId);
        OrderDetail orderDetail=new OrderDetail();
        orderDetail.setPrice(product.getPrice());
        orderDetail.setOrder(order);
        orderDetail.setProduct(product);
        for (OrderDetail item:order.getOrderDetails()) {
            if(item.getProduct().getId()==product.getId()){
                orderDetail.setId(item.getId());
                orderDetail.setQuantity(item.getQuantity()+detailDTO.getQuantity());
                try {
                    orderDetailRepository.save(orderDetail);
                    result.setMessage("Update Order-detail success");
                    result.setSuccess(true);
                } catch(Exception e){
                    result.setMessage("Update Order-detail false: "+e);
                    result.setSuccess(false);
                }
                return result;
            }
        }
        orderDetail.setQuantity(detailDTO.getQuantity());
        try {
            orderDetailRepository.save(orderDetail);
            result.setMessage("Create Order-detail success");
            result.setSuccess(true);
        } catch(Exception e){
            result.setMessage("Create Order-detail false: "+e);
            result.setSuccess(false);
        }
        return result;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public DataApiResult deleteODD(Long orderDDId){
        DataApiResult result =new DataApiResult();
        try {
            orderDetailRepository.deleteById(orderDDId);
            result.setMessage("Delete Order-detail success");
            result.setSuccess(true);
        } catch(Exception e){
            result.setMessage("Delete Order-detail false: "+e);
            result.setSuccess(false);
        }
        return result;
    }
}
