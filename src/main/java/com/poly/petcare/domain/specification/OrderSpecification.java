package com.poly.petcare.domain.specification;

import com.poly.petcare.domain.entites.Order;
import com.poly.petcare.domain.entites.Product;
import org.springframework.data.jpa.domain.Specification;

public class OrderSpecification {
    /**
     * Lọc ra danh sách order theo status
     * @param status
     * @return
     */
    public static Specification<Order> hasOrderByStatus(Integer status) {
        return (root, query, cb) -> cb.equal(root.get("status"), status);
    }

    /**
     * Tìm kiếm đơn hàng theo số điện thoại
     * @param phoneNumber
     * @return
     */
    public static Specification<Order> hasOrderByPhone(String phoneNumber) {
        return (root, query, cb) -> cb.like(root.get("phoneNumber"), "%"+phoneNumber+"%");
    }
}
