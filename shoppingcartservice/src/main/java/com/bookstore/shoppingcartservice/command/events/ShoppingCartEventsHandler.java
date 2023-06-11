package com.bookstore.shoppingcartservice.command.events;

import com.bookstore.commonservice.event.ShoppingCartDeleteByUserEvent;
import com.bookstore.shoppingcartservice.command.data.ShoppingCart;
import com.bookstore.shoppingcartservice.command.data.ShoppingCartRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShoppingCartEventsHandler {

    @Autowired
    private ShoppingCartRepository repository;


    @EventHandler
    public void on(ShoppingCartCreateEvent event) {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(event, shoppingCart);
        repository.save(shoppingCart);
    }

    @EventHandler
    public void on(ShoppingCartUpdateEvent event) {
        ShoppingCart shoppingCart =
                repository.findByUserIdAndProductId(event.getUserId(),
                        event.getProductId());
        shoppingCart.setQuantity(event.getQuantity());
        repository.save(shoppingCart);
    }

    @EventHandler
    public void on(ShoppingCartDeleteEvent event) {
        try {
            repository.deleteById(event.getId());
        } catch (Exception e) {
            // Xử lý ngoại lệ ở đây
            e.printStackTrace(); // Hoặc sử dụng logger để ghi log
            // Các hành động khác (nếu cần)
        }
    }
    @EventHandler
    public void on(ShoppingCartDeleteByUserEvent event) {
        try {
            repository.deleteByUserId(event.getUserId());
        } catch (Exception e) {
            // Xử lý ngoại lệ ở đây
            e.printStackTrace(); // Hoặc sử dụng logger để ghi log
            // Các hành động khác (nếu cần)
        }
    }
}
