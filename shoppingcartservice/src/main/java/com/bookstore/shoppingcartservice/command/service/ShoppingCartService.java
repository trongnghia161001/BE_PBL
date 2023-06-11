package com.bookstore.shoppingcartservice.command.service;

import com.bookstore.shoppingcartservice.command.data.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartService implements IShoppingCartService{


    @Autowired
    private ShoppingCartRepository repository;

    public String findIdShoppingCart(Long userId, String productId) {
        return	repository.findByUserIdAndProductId(userId, productId).getId();
    }

}
