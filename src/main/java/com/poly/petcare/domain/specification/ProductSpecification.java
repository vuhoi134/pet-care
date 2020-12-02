package com.poly.petcare.domain.specification;

import com.poly.petcare.domain.entites.Product;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class ProductSpecification {
    /**
     * Lấy ra danh sách product có ExpiryDate > thời gian hiện tại
     * @param
     * @return
     */
    public static Specification<Product> hasProductName(String productName) {
        return (root, query, cb) -> cb.like(root.get("name"), productName);
    }
}
