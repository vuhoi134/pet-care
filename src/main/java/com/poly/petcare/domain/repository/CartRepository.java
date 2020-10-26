package com.poly.petcare.domain.repository;

import com.poly.petcare.domain.entites.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserName(String userName);

    @Query(value = "SELECT * FROM dbo_cart c " +
            "WHERE :guid IS NULL OR c.guid = :guid " +
            "ORDER BY c.cart_id DESC LIMIT 1", nativeQuery = true)
    Cart findFirstCartByGuid(@Param("guid") String guid);
}
