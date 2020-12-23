package com.poly.petcare.domain.repository;

import com.poly.petcare.domain.entites.Order;
import com.poly.petcare.domain.entites.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long>, JpaSpecificationExecutor<Order> {
    Order findByGuid(String guid);

    List<Order> findByUser(User user, Sort sort);

    @Query(value = "select convert(SUBSTRING_INDEX(code,'ODR',-1) , UNSIGNED INTEGER) as c from dbo_order where code like '%ODR%' order by c desc limit 0,1",nativeQuery = true)
    Long getCodeMax();

    @Query(value = "Select sum(od.total_money) as totalMoney,count(od.id) as totalOrder from dbo_order as od\n" +
            "where od.last_modified_datetime > \"2020-12-18 00:00:00.00\" and od.last_modified_datetime < \"2020-12-18 23:59:00:00\"",nativeQuery = true)
    DashBoard dashBoardDay(String date1,String date2);

    @Query(value = "Select sum(od.total_money) as money from dbo_order as od\n" +
            "where od.last_modified_datetime > \"2020-12-18 00:00:00.00\" and od.last_modified_datetime < \"2020-12-18 23:59:00:00\"",nativeQuery = true)
    DashBoard dashBoardWeek(String date1,String date2);

    @Query(value = "Select sum(od.total_money) as totalMoney,count(od.id) from dbo_order as od\n" +
            "where od.last_modified_datetime > \"2020-12-01\" and od.last_modified_datetime < \"2020-12-30\"",nativeQuery = true)
    DashBoard dashBoardMonth(String date1,String date2);

    interface DashBoard{
        Long getTotalMoney();
        Long getTotalOrder();
    }
}
