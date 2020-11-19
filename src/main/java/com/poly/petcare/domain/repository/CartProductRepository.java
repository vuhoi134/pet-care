package com.poly.petcare.domain.repository;

import com.poly.petcare.domain.entites.Cart;
import com.poly.petcare.domain.entites.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartProductRepository extends JpaRepository<CartProduct,Long> {
    @Query(value = "SELECT * FROM dbo_cart_product cp " +
            "WHERE cp.cart_id = :cartId  " +
            "AND cp.product_id = :productId " +
            "ORDER BY cp.id DESC LIMIT 1",nativeQuery = true)
    CartProduct findFirstCartProductByCartIdAndProductId(@Param("cartId") long cartId, @Param("productId") long productId);

    List<CartProduct> findAllByCart(Cart cart);
}
