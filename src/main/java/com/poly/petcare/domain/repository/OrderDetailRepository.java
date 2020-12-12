package com.poly.petcare.domain.repository;

import com.poly.petcare.domain.entites.Order;
import com.poly.petcare.domain.entites.OrderDetail;
import com.poly.petcare.domain.entites.ProductStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,Long>, JpaSpecificationExecutor<OrderDetail> {
    List<OrderDetail> findByOrder(Order order);

    @Query(value="select od.product_id as productId,count(*) as amount from dbo_order_detail as od left join dbo_product as p on od.product_id=p.id \n" +
            "where p.category_id= :categoryId \n" +
            "group by productId order by amount desc limit 4;",nativeQuery=true)
    List<ODD> topProduct(@Param("categoryId") Long categoryId);

    interface ODD{
        Long getAmount();
        Long getProductId();
    }
}
