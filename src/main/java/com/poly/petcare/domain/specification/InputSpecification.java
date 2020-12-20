package com.poly.petcare.domain.specification;

import com.poly.petcare.domain.entites.Input;
import com.poly.petcare.domain.entites.Product;
import org.springframework.data.jpa.domain.Specification;

public class InputSpecification {
    /**
     * Lấy ra danh sách product với status
     * @param status
     * @return
     */
    public static Specification<Input> hasInputByStatus(Integer status) {
        return (root, query, cb) -> cb.equal(root.get("status"),status);
    }

    /**
     * Lấy ra danh sách product với mã phiếu
     * @param name
     * @return
     */
    public static Specification<Input> hasInputByUserName(String name) {
        return (root, query, cb) -> cb.like(root.get("user").get("profile").get("fullName"),"%"+name+"%");
    }
}
