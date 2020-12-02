package com.poly.petcare.domain.specification;

import com.poly.petcare.domain.entites.Product;
import org.springframework.data.jpa.domain.Specification;

public class OrderDetailSpecification {
    /**
     * Lấy ra danh sách product theo categoryId
     * @param id
     * @return
     */
    public static Specification<Product> hasOrderDetailByOrderId(long id) {
        return (root, query, cb) -> cb.equal(root.get("order").get("id"), id);
    }
}
