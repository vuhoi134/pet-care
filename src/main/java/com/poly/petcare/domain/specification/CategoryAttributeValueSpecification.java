package com.poly.petcare.domain.specification;

import com.poly.petcare.domain.entites.Product;
import org.springframework.data.jpa.domain.Specification;

public class CategoryAttributeValueSpecification {

    /**
     * Lấy ra danh sách product theo categoryAttributeValueId
     * @param id
     * @return
     */
    public static Specification<Product> hasProductCategoryAttributeValue(long id) {
        return (root, query, cb) -> cb.equal(root.get("products").get("categoryAttributeValues").get("id"), id);
    }
}
