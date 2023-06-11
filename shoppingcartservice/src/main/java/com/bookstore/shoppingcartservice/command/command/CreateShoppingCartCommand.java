package com.bookstore.shoppingcartservice.command.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateShoppingCartCommand {

    @TargetAggregateIdentifier
    private String id;

    private Long userId;

    private String productId;

    private int quantity;

    private Date createdAt;

    private Date updatedAt;
}
