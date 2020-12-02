package com.poly.petcare.domain.specification;

import com.poly.petcare.domain.entites.Product;
import org.springframework.data.jpa.domain.Specification;

public class OrderSpecification {
    /**
     * Lọc ra danh sách order theo status
     * @param status
     * @return
     */
    public static Specification<Product> hasOrderByStatus(Integer status) {
        return (root, query, cb) -> cb.equal(root.get("status"), status);
    }
}
