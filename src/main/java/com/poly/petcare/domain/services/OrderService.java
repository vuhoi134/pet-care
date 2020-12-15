package com.poly.petcare.domain.services;

import com.poly.petcare.app.dtos.OrderDTO;
import com.poly.petcare.app.dtos.OrderDetailDTO;
import com.poly.petcare.app.responses.OrderDetailResponse;
import com.poly.petcare.app.responses.OrderResponse;
import com.poly.petcare.app.responses.ProductResponse;
import com.poly.petcare.app.result.BaseApiResult;
import com.poly.petcare.app.result.DataApiResult;
import com.poly.petcare.domain.entites.*;
import com.poly.petcare.domain.mapper.OrderDetailMapper;
import com.poly.petcare.domain.mapper.OrderMapper;
import com.poly.petcare.domain.mapper.ProductMapper;
import com.poly.petcare.domain.repository.OrderRepository;
import com.poly.petcare.domain.specification.OrderDetailSpecification;
import com.poly.petcare.domain.specification.OrderSpecification;
import com.poly.petcare.domain.utils.ConverCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class OrderService extends BaseServices{
    private static final Logger logger = LogManager.getLogger(OrderService.class);
    private Long codeMax;
    private OrderDetailMapper orderDetailMapper;
    private OrderMapper orderMapper;
    private ProductMapper productMapper;
    private OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderDetailMapper orderDetailMapper,ProductMapper productMapper,OrderRepository orderRepository,OrderMapper orderMapper){
        try {
            this.orderDetailMapper = orderDetailMapper;
            this.productMapper = productMapper;
            this.orderRepository =orderRepository;
            this.orderMapper = orderMapper;
        } finally {
            codeMax = orderRepository.getCodeMax();
            if (codeMax == null) {
                codeMax = 0L;
            }
        }
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public BaseApiResult createOrder(OrderDTO orderDTO){
        codeMax += 1;
        BaseApiResult result = new BaseApiResult();
        if(orderDTO.getUserId()==0){ // khách hàng vãng lai
            Order order=orderRepository.findByGuid(orderDTO.getGuid());
            if(order != null){
//                for (OrderDetailDTO item:orderDTO.getOrderDetailDTOS()) {
//                    OrderDetail orderDetail=new OrderDetail();
//                    orderDetail.setOrder(order);
//                    orderDetail.setPrice(item.getPrice());
//                    orderDetail.setQuantity(item.getQuantity());
//                    orderDetail.setProduct(productRepository.getOne(item.getProductId()));
//                    orderDetailRepository.save(orderDetail);
//                }
            }else{
                String pass=String.valueOf(new Date().getTime());
                User user=new User();
                user.setUserName(orderDTO.getEmail());
                user.setPassWord(passwordEncoder.encode(pass));
                User user1=userRepository.save(user);
                Order order1=new Order();
                order1.setGuid(orderDTO.getGuid());
                order1.setCode(ConverCode.convertCode(codeMax, "", "ODR"));
                order1.setUserName(orderDTO.getUserName());
                order1.setEmail(orderDTO.getEmail());
                order1.setAddress(orderDTO.getAddress()+" "+orderDTO.getCity());
                order1.setPhoneNumber(orderDTO.getPhoneNumber());
                order1.setTotalMoney(orderDTO.getTotalMoney());
                order1.setDiscount(orderDTO.getDiscount());
                order1.setStatus(orderDTO.getStatus());
                order1.setUser(user1);
                Order order2=orderRepository.save(order1);
                for (long id:orderDTO.getTransactionId()) {
                    OrderTransaction orderTransaction=new OrderTransaction();
                    orderTransaction.setOrderId(order2.getId());
                    orderTransaction.setTransactionId(id);
                    orderTransactionRepository.save(orderTransaction);
                }
                List<OrderDetail> orderDetailList=new ArrayList<>();
                for (OrderDetailDTO item:orderDTO.getOrderDetailDTOS()) {
                    OrderDetail orderDetail=new OrderDetail();
                    ProductStore productStore = productStoreRepository.getOne(item.getProductId());
                    orderDetail.setOrder(order2);
                    orderDetail.setPrice(item.getPrice());
                    orderDetail.setQuantity(item.getQuantity());
                    orderDetail.setProduct(productRepository.getOne(item.getProductId()));
                    orderDetailRepository.save(orderDetail);

                    //trừ số lượng sản phẩm trên cửa hàng
                    productStore.setQuantityStore(productStore.getQuantityStore()-item.getQuantity());
                    productStoreRepository.saveAndFlush(productStore);
                    orderDetailList.add(orderDetail);
                }
                Profile profile=new Profile();
                profile.setUser(user1);
                profileRepository.save(profile);
                // xóa giỏ hàng
                Cart cart = cartRepository.findByGuid(orderDTO.getGuid());
                cartRepository.delete(cart);

                // sent mail đơn hàng và mail tài khoản
                Integer totalOrderMoney=0;
                String textTTDH="Mã đơn hàng của quý khách là: "+order2.getCode()+"\n";
                textTTDH+="Sản phẩm đặt hàng: ";

                for (OrderDetail od:orderDetailList) {
                    textTTDH+="   - Tên sản phẩm: "+od.getProduct().getName()+"\n"+
                            "      Giá: "+od.getPrice()+" VNĐ"+"\n"+
                            "      Số lượng: "+od.getQuantity()+"\n"+
                            "      Thành tiền: "+od.getQuantity()*od.getPrice().intValue()+"\n"+
                            "\n";
                    totalOrderMoney+=od.getQuantity()*od.getPrice().intValue();
                }
                textTTDH+="Tổng giá trị đơn hàng: "+totalOrderMoney+" VNĐ";

                String textTK="Tài khoản: "+user1.getUserName()+"\n"+"Mật khẩu: "+pass;
                if(mailSerivce.sendSimpleEmail(orderDTO.getEmail(),"Thông tin đơn hàng",textTTDH)&&
                        mailSerivce.sendSimpleEmail(orderDTO.getEmail(),"Thông tin tài khoản",textTK)){
                    result.setMessage("Create Order Success!");
                    result.setSuccess(true);
                    return result;
                }

            }
        }else{ // khách hàng có tài khoản
            List<Transaction> transactionList=new ArrayList<>();
            transactionList.add(transactionRepository.getOne(orderDTO.getTransactionId()[0]));
            User user=userRepository.getOne(orderDTO.getUserId());
            Order order1=new Order();
            order1.setCode(ConverCode.convertCode(codeMax, "", "ODR"));
            order1.setUser(user);
            order1.setGuid(orderDTO.getGuid());
            order1.setUserName(orderDTO.getUserName());
            order1.setEmail(orderDTO.getEmail());
            order1.setAddress(orderDTO.getAddress()+" "+orderDTO.getCity());
            order1.setPhoneNumber(orderDTO.getPhoneNumber());
            order1.setTotalMoney(orderDTO.getTotalMoney());
            order1.setDiscount(orderDTO.getDiscount());
            order1.setStatus(orderDTO.getStatus());
            Order order2=orderRepository.save(order1);
            for (long id:orderDTO.getTransactionId()) {
                OrderTransaction orderTransaction=new OrderTransaction();
                orderTransaction.setOrderId(order2.getId());
                orderTransaction.setTransactionId(id);
                orderTransactionRepository.save(orderTransaction);
            }
            List<OrderDetail> orderDetailList=new ArrayList<>();
            for (OrderDetailDTO item:orderDTO.getOrderDetailDTOS()) {
                OrderDetail orderDetail=new OrderDetail();
                ProductStore productStore = productStoreRepository.getOne(item.getProductId());
                orderDetail.setOrder(order2);
                orderDetail.setPrice(item.getPrice());
                orderDetail.setQuantity(item.getQuantity());
                orderDetail.setProduct(productRepository.getOne(item.getProductId()));
                orderDetailRepository.save(orderDetail);

                //trừ số lượng sản phẩm trên cửa hàng
                productStore.setQuantityStore(productStore.getQuantityStore()-item.getQuantity());
                productStoreRepository.saveAndFlush(productStore);
                orderDetailList.add(orderDetail);
            }
            // xóa giỏ hàng chi tiết
//            Sort sort = Sort.by("id").descending();
            Cart cart = cartRepository.findByUser(user);
            List<CartProduct> cartProductList=cartProductRepository.findAllByCart(cart);
            for (CartProduct cp:cartProductList) {
                cartProductRepository.delete(cp);
            }

            // sent mail đơn hàng
            Integer totalOrderMoney=0;
            String textTTDH="Mã đơn hàng của quý khách là: "+order2.getCode()+"\n";
            textTTDH+="Sản phẩm đặt hàng: \n";
            for (OrderDetail od:orderDetailList) {
                textTTDH+="   - Tên sản phẩm: "+od.getProduct().getName()+"\n"+
                        "      Giá: "+od.getPrice()+" VNĐ"+"\n"+
                        "      Số lượng: "+od.getQuantity()+"\n"+
                        "      Thành tiền: "+od.getQuantity()*od.getPrice().intValue()+"\n"+
                        "\n";
            }
            textTTDH+="Tổng giá trị đơn hàng: "+totalOrderMoney+" VNĐ";
            if(mailSerivce.sendSimpleEmail(orderDTO.getEmail(),"Thông tin đơn hàng",textTTDH)){
                result.setMessage("Create Order Success!");
                result.setSuccess(true);
                return result;
            }
        }
        result.setMessage("Fail!");
        result.setSuccess(false);
        return result;
    }

    public DataApiResult getListOrder(String guid, Long userId){
        Sort sort = Sort.by("id").descending();
        DataApiResult result = new DataApiResult();
        if(guid != null && userId==0){
//            Order order=orderRepository.findByGuid(guid);
//            Specification conditions = Specification.where(OrderDetailSpecification.hasOrderDetailByOrderId(order.getId()));
//            List<OrderDetail> listOrderDetail=orderDetailRepository.findAll(conditions,sort);
//            List<OrderDetailResponse> list=new ArrayList<>();
//            for (OrderDetail item:listOrderDetail) {
//                OrderDetailResponse orderDetailResponse=new OrderDetailResponse();
//                orderDetailResponse=orderDetailMapper.convertToDTO(item);
//                orderDetailResponse.setProductResponse(productMapper.convertToDTO(item.getProduct()));
//                list.add(orderDetailResponse);
//            }
            Order order=orderRepository.findByGuid(guid);
            List<OrderResponse> list=new ArrayList<>();
                OrderResponse orderResponse=orderMapper.convertToDTO(order);
                List<OrderDetailResponse> listODR=new ArrayList<>();
                for (OrderDetail itemOD:order.getOrderDetails()) {
                    OrderDetailResponse orderDetailResponse=new OrderDetailResponse();
                    orderDetailResponse=orderDetailMapper.convertToDTO(itemOD);
                    ProductResponse productResponse=productMapper.convertToDTO(itemOD.getProduct());
                    productResponse.setProductImageList(null);
                    orderDetailResponse.setProductResponse(productResponse);
                    listODR.add(orderDetailResponse);
                }
                orderResponse.setOrderDetailResponses(listODR);
                list.add(orderResponse);

            result.setMessage("List OrderDetail!");
            result.setSuccess(true);
            result.setData(list);
            return result;
        }else if(userId>0){
            User user=userRepository.getOne(userId);
            List<Order> order=orderRepository.findByUser(user,sort);
            List<OrderResponse> list=new ArrayList<>();
            for (Order item:order) {
                OrderResponse orderResponse=orderMapper.convertToDTO(item);
                List<OrderDetailResponse> listODR=new ArrayList<>();
                for (OrderDetail itemOD:item.getOrderDetails()) {
                    OrderDetailResponse orderDetailResponse=new OrderDetailResponse();
                    orderDetailResponse=orderDetailMapper.convertToDTO(itemOD);
                    ProductResponse productResponse=productMapper.convertToDTO(itemOD.getProduct());
                    productResponse.setProductImageList(null);
                    orderDetailResponse.setProductResponse(productResponse);
                    listODR.add(orderDetailResponse);
                }
                orderResponse.setOrderDetailResponses(listODR);
                list.add(orderResponse);
            }
            result.setMessage("List OrderDetail!");
            result.setSuccess(true);
            result.setData(list);
            return result;
        }
        result.setMessage("Fail!");
        result.setSuccess(false);
        return result;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public BaseApiResult createOrderStore(OrderDTO orderDTO){
            codeMax += 1;
        BaseApiResult result = new BaseApiResult();
                Order order1=new Order();
                order1.setGuid(orderDTO.getGuid());
                order1.setCode(ConverCode.convertCode(codeMax, "", "ODR"));
                order1.setUserName(orderDTO.getUserName());
                order1.setEmail(orderDTO.getEmail());
                order1.setAddress(orderDTO.getAddress()+" "+orderDTO.getCity());
                order1.setPhoneNumber(orderDTO.getPhoneNumber());
                order1.setTotalMoney(orderDTO.getTotalMoney());
                order1.setDiscount(orderDTO.getDiscount());
                order1.setStatus(orderDTO.getStatus());
                Order order2=orderRepository.save(order1);

                for (long id:orderDTO.getTransactionId()) {
//                    transactionList.add(transactionRepository.getOne(id));
                    OrderTransaction orderTransaction=new OrderTransaction();
                    orderTransaction.setOrderId(order2.getId());
                    orderTransaction.setTransactionId(id);
                    orderTransactionRepository.save(orderTransaction);
                }

                for (OrderDetailDTO item:orderDTO.getOrderDetailDTOS()) {
                    ProductStore productStore = productStoreRepository.getOne(item.getProductId());
                    OrderDetail orderDetail=new OrderDetail();
                    orderDetail.setOrder(order2);
                    orderDetail.setPrice(item.getPrice());
                    orderDetail.setQuantity(item.getQuantity());
                    orderDetail.setProduct(productRepository.getOne(item.getProductId()));

                    //trừ số lượng sản phẩm trên cửa hàng
                    productStore.setQuantityStore(productStore.getQuantityStore()-item.getQuantity());
                    productStoreRepository.saveAndFlush(productStore);

                    orderDetailRepository.save(orderDetail);
                }
                Cart cart = cartRepository.findByGuid(orderDTO.getGuid());
                cartRepository.delete(cart);
                result.setMessage("Create Order Success!");
                result.setSuccess(true);
                return result;
    }

    public DataApiResult getAllListOrder(Integer page,Integer limit,Integer status,String phoneNumber){
        DataApiResult result = new DataApiResult();
        Sort sort = Sort.by("id").descending();
        Pageable pageable = PageRequest.of(page, limit,sort);
        if (status==0 && phoneNumber.length()==0){
            Page<Order> listOrder=orderRepository.findAll(pageable);
            List<OrderResponse> list=new ArrayList<>();
            for (Order item:listOrder) {
                OrderResponse orderResponse=new OrderResponse();
                orderResponse=orderMapper.convertToDTO(item);
                list.add(orderResponse);
            }
            result.setMessage("List Order!");
            result.setSuccess(true);
            result.setTotalItem(listOrder.getTotalElements());
            result.setData(list);
            return result;
        }else if(status>0 && phoneNumber.length()==0) {
            Specification conditions = Specification.where(OrderSpecification.hasOrderByStatus(status));
            Page<Order> listOrder = orderRepository.findAll(conditions, pageable);
            List<OrderResponse> list = new ArrayList<>();
            for (Order item : listOrder) {
                OrderResponse orderResponse = new OrderResponse();
                orderResponse = orderMapper.convertToDTO(item);
                list.add(orderResponse);
            }
            result.setMessage("List Order!");
            result.setSuccess(true);
            result.setTotalItem(listOrder.getTotalElements());
            result.setData(list);
            return result;
        }else if(status==0 && phoneNumber.length()>0 ) {
            Specification conditions = Specification.where(OrderSpecification.hasOrderByPhone(phoneNumber));
            Page<Order> listOrder = orderRepository.findAll(conditions, pageable);
            List<OrderResponse> list = new ArrayList<>();
            for (Order item : listOrder) {
                OrderResponse orderResponse = new OrderResponse();
                orderResponse = orderMapper.convertToDTO(item);
                list.add(orderResponse);
            }
            result.setMessage("List Order!");
            result.setSuccess(true);
            result.setTotalItem(listOrder.getTotalElements());
            result.setData(list);
            return result;
        }else if(status>0 && phoneNumber.length()>0 ) {
            Specification conditions = Specification.where(OrderSpecification.hasOrderByStatus(status).and(
                    OrderSpecification.hasOrderByPhone(phoneNumber)));
            Page<Order> listOrder = orderRepository.findAll(conditions, pageable);
            List<OrderResponse> list = new ArrayList<>();
            for (Order item : listOrder) {
                OrderResponse orderResponse = new OrderResponse();
                orderResponse = orderMapper.convertToDTO(item);
                list.add(orderResponse);
            }
            result.setMessage("List Order!");
            result.setSuccess(true);
            result.setTotalItem(listOrder.getTotalElements());
            result.setData(list);
            return result;
        }else {
            result.setMessage("Not List Order!");
            result.setSuccess(false);
            return result;
        }
    }

    public DataApiResult orderDetail(Long orderId){
        Long totalMoney=0L;
        DataApiResult result = new DataApiResult();
        Order order=orderRepository.getOne(orderId);
        OrderResponse orderResponse=orderMapper.convertToDTO(order);
        List<OrderDetailResponse> list=new ArrayList<>();
        for (OrderDetail item:order.getOrderDetails()) {
            OrderDetailResponse detailResponse=orderDetailMapper.convertToDTO(item);
            detailResponse.setProductResponse(productMapper.convertToDTO(item.getProduct()));
            totalMoney+=(Long.valueOf(item.getQuantity())*item.getPrice().intValue()) ;
            list.add(detailResponse);
        }
        orderResponse.setOrderDetailResponses(list);
        result.setSuccess(true);
        result.setMessage("Order detail");
        result.setData(orderResponse);
        result.setTotalMoney(totalMoney);
        return result;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public BaseApiResult updateOrderStore(Long orderId,OrderDTO orderDTO){
        BaseApiResult result = new BaseApiResult();
        Order order=orderRepository.getOne(orderId);

        // xóa đơn hàng chi tiết cũ
        for (OrderDetail o:order.getOrderDetails()) {
            orderDetailRepository.delete(o);
        }

        List<Transaction> transactionList=new ArrayList<>();
        for (long id:orderDTO.getTransactionId()) {
            transactionList.add(transactionRepository.getOne(id));
        }
        Order order1=new Order();
        order1.setId(orderId);
        order1.setGuid(order.getGuid());
        order1.setCode(order.getCode());
        order1.setUserName(orderDTO.getUserName());
        order1.setEmail(orderDTO.getEmail());
        order1.setAddress(orderDTO.getAddress()+" "+orderDTO.getCity());
        order1.setPhoneNumber(orderDTO.getPhoneNumber());
        order1.setTotalMoney(orderDTO.getTotalMoney());
        order1.setDiscount(orderDTO.getDiscount());
        order1.setStatus(orderDTO.getStatus());
        Order order2=orderRepository.saveAndFlush(order1);

        // tạo đơn hàng chi tiết mới
        for (OrderDetailDTO item:orderDTO.getOrderDetailDTOS()) {
            OrderDetail orderDetail=new OrderDetail();
            orderDetail.setOrder(order2);
            orderDetail.setPrice(item.getPrice());
            orderDetail.setQuantity(item.getQuantity());
            orderDetail.setProduct(productRepository.getOne(item.getProductId()));
            System.out.println(item.getQuantity()+" Có mà");
            orderDetailRepository.saveAndFlush(orderDetail);
        }
        result.setMessage("Update Order Success!");
        result.setSuccess(true);
        return result;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public DataApiResult updateODD(Long orderDId,Integer quantity){
        DataApiResult result=new DataApiResult();
        OrderDetail orderDetail=orderDetailRepository.getOne(orderDId);
        orderDetail.setQuantity(quantity);
        orderDetailRepository.saveAndFlush(orderDetail);
        result.setMessage("Update order-detail success");
        result.setSuccess(true);
        return result;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public DataApiResult updateStatusODD(Long orderId,Integer status){
        DataApiResult result=new DataApiResult();
        Order order=orderRepository.getOne(orderId);
        order.setStatus(status);
        orderRepository.saveAndFlush(order);
        result.setMessage("Update status order success");
        result.setSuccess(true);
        return result;
    }
}
