package com.bookstore.shoppingcartservice.command.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, String> {
    List<ShoppingCart> findByUserId(Long userId);

    ShoppingCart findByUserIdAndProductId(Long userId, String productId);

    void deleteByUserId(Long userId);

//    ShoppingCart findByUser_idAndProduct_id(String userId, String productId);
}
