package com.ecommerce.project.repositories;

import com.ecommerce.project.Model.cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CartRepository extends JpaRepository<cart,Long> {

    @Query("SELECT c FROM cart c WHERE c.user.email = ?1")
    cart findCartByEmail(String email);

    @Query("SELECT c FROM cart c WHERE c.user.email = ?1 AND c.cartId =?2")
    cart findCartByEmailAndCartId(String emailId, Long cartId);

    @Query("SELECT c FROM cart c JOIN FETCH c.cartItems ci JOIN FETCH ci.products p WHERE p.productId =?1")
    List<cart> findCartsByProductId(Long productId);
}
