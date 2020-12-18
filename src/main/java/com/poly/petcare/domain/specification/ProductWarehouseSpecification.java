package com.poly.petcare.domain.specification;

import com.poly.petcare.domain.entites.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductWarehouseSpecification {
    /**
     * Lấy ra danh sách product theo đối tượng tìm kiếm
     * @param content
     * @return
     */
    public static Specification<Product> hasProductByName(String content) {
        return (root, query, cb) -> cb.like(root.get("products").get("name"), "%" + content + "%");
    }

//    /**
//     * Lấy ra danh sách product với ngày hết hạn giảm dần
//     * @param content
//     * @return
//     */
//    public static Specification<Product> hasProductByName(String content) {
//        return (root, query, cb) -> cb.like(root.get("products").get("name"), "%" + content + "%");
//    }
}
