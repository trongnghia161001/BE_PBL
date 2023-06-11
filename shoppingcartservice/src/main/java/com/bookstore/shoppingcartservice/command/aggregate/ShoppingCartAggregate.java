package com.bookstore.shoppingcartservice.command.aggregate;

import com.bookstore.commonservice.command.DeleteShoppingCartByUserCommand;
import com.bookstore.commonservice.event.ShoppingCartDeleteByUserEvent;
import com.bookstore.shoppingcartservice.command.command.CreateShoppingCartCommand;
import com.bookstore.shoppingcartservice.command.command.DeleteShoppingCartCommand;
import com.bookstore.shoppingcartservice.command.command.UpdateShoppingCartCommand;
import com.bookstore.shoppingcartservice.command.events.ShoppingCartCreateEvent;
import com.bookstore.shoppingcartservice.command.events.ShoppingCartDeleteEvent;
import com.bookstore.shoppingcartservice.command.events.ShoppingCartUpdateEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Aggregate
public class ShoppingCartAggregate {

    @AggregateIdentifier
    private String id;

    private Long userId;

    private String productId;

    private int quantity;

    private Date createdAt;

    private Date updatedAt;

    private String message;

    public ShoppingCartAggregate() {

    }

    @CommandHandler
    public ShoppingCartAggregate(CreateShoppingCartCommand command) {
        ShoppingCartCreateEvent event = new ShoppingCartCreateEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(ShoppingCartCreateEvent event) {
        this.id = event.getId();
        this.userId = event.getUserId();
        this.productId = event.getProductId();
        this.quantity = event.getQuantity();
    }

    @CommandHandler
    public void handler(UpdateShoppingCartCommand command) {
        ShoppingCartUpdateEvent event = new ShoppingCartUpdateEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(ShoppingCartUpdateEvent event) {
        this.id = event.getId();
        this.userId = event.getUserId();
        this.productId = event.getProductId();
        this.quantity = event.getQuantity();
    }

    @CommandHandler
    public void handler(DeleteShoppingCartCommand command) {
        ShoppingCartDeleteEvent event = new ShoppingCartDeleteEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(ShoppingCartDeleteEvent event) {
        this.id = event.getId();
    }

    @CommandHandler
    public void handler(DeleteShoppingCartByUserCommand command) {
        ShoppingCartDeleteByUserEvent event = new ShoppingCartDeleteByUserEvent();
        event.setUserId(command.getUserId());
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(ShoppingCartDeleteByUserEvent event) {
        this.userId = event.getUserId();
    }
}