package com.ecommerce.project.repositories;

import com.ecommerce.project.Model.cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface CartRepository extends JpaRepository<cart,Long> {

    @Query("SELECT c FROM cart c WHERE c.user.email = ?1")
    cart findCartByEmail(String email);

}
