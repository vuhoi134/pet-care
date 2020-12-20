package com.poly.petcare.domain.specification;

import com.poly.petcare.domain.entites.Input;
import com.poly.petcare.domain.entites.Output;
import org.springframework.data.jpa.domain.Specification;

public class OutputSpecification {
    /**
     * Lấy ra danh sách product với status
     * @param status
     * @return
     */
    public static Specification<Output> hasOutputByStatus(Integer status) {
        return (root, query, cb) -> cb.equal(root.get("status"),status);
    }

    /**
     * Lấy ra danh sách product với mã phiếu
     * @param name
     * @return
     */
    public static Specification<Output> hasOutputByUserName(String name) {
        return (root, query, cb) -> cb.like(root.get("user").get("profile").get("fullName"),"%"+name+"%");
    }
}
