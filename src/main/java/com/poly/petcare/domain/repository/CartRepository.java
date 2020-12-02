package com.poly.petcare.domain.repository;

import com.poly.petcare.domain.entites.Cart;
import com.poly.petcare.domain.entites.Profile;
import com.poly.petcare.domain.entites.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserName(String userName);

    @Query(value = "SELECT * FROM dbo_cart c " +
            "WHERE :guid IS NULL OR c.guid = :guid " +
            "ORDER BY c.id DESC LIMIT 1", nativeQuery = true)
    Cart findFirstCartByGuid(@Param("guid") String guid);

    Cart findByGuid(String guid);
    Cart findByUser(User user);
    List<Cart> findAllByUser(User user, Sort sort);
//    Cart findByGuidAndUser(String guid,User user);
}
