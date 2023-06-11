package com.bookstore.shoppingcartservice.command.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartUpdateEvent {

    private String id;

    private Long userId;

    private String productId;

    private int quantity;

    private Date createdAt;

    private Date updatedAt;
}
