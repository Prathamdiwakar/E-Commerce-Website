package com.ecommerce.project.repositories;

import com.ecommerce.project.Model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Query("SELECT ci FROM CartItem ci WHERE ci.carts.cartId = ?1 AND ci.products.productId = ?2")
    CartItem findCartItemByProductIdAndCartId(Long cartId, Long productId);
}
