package com.poly.petcare.domain.specification;

import com.poly.petcare.domain.entites.Profile;
import org.springframework.data.jpa.domain.Specification;

public class ProfileSpecification {
    /**
     * Lấy ra thông tin profile theo userId
     * @param userId
     * @return
     */
    public static Specification<Profile> hasProfile(Long userId){
            return (root, query, cb) -> cb.equal(root.get("user").get("id"), userId);
    }

    /**
     * Lấy ra thông tin profile theo userRole
     * @param userId
     * @return
     */
    public static Specification<Profile> hasListProfile(Long userId){
        return (root, query, cb) -> cb.equal(root.get("user").get("roles"), userId);
    }
}
