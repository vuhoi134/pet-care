package com.poly.petcare.domain.specification;

import com.poly.petcare.domain.entites.Product;
import org.springframework.data.jpa.domain.Specification;

public class CategorySpecification {

    /**
     * Lấy ra danh sách product theo categoryId
     * @param id
     * @return
     */
    public static Specification<Product> hasProductCategory(long id) {
        return (root, query, cb) -> cb.equal(root.get("products").get("category").get("id"), id);
    }

    /**
     * Lấy ra category theo categoryId và level
     * @param id
     * @return
     */
    public static Specification<Product> hasCategory(String key,long id) {
        return (root, query, cb) -> cb.equal(root.get(key), id);
    }
}
