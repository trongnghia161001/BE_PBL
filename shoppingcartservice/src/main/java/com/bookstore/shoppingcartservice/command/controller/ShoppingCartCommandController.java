package com.bookstore.shoppingcartservice.command.controller;

import com.bookstore.shoppingcartservice.command.command.CreateShoppingCartCommand;
import com.bookstore.shoppingcartservice.command.command.DeleteShoppingCartCommand;
import com.bookstore.shoppingcartservice.command.command.UpdateShoppingCartCommand;
import com.bookstore.shoppingcartservice.command.model.ShoppingCartRequestModel;
import com.bookstore.shoppingcartservice.command.service.ShoppingCartService;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/shoppingCart")
public class ShoppingCartCommandController {

    @Autowired
    private CommandGateway commandGateway;

    @Autowired
    private ShoppingCartService shoppingCartService;


    @PostMapping
    public String addBook(@RequestBody ShoppingCartRequestModel model) {
        CreateShoppingCartCommand command = new CreateShoppingCartCommand();
        BeanUtils.copyProperties(model, command);
        command.setId(UUID.randomUUID().toString());
        commandGateway.sendAndWait(command);
        return "Add";
    }

    @PutMapping
    public String updateBook(@RequestBody ShoppingCartRequestModel model) {
        String shoppingCartId =
                shoppingCartService.findIdShoppingCart(model.getUserId(),
                        model.getProductId());
        UpdateShoppingCartCommand command = new UpdateShoppingCartCommand(
                shoppingCartId, model.getUserId(), model.getProductId(),
                model.getQuantity(), new Date(), new Date());

        commandGateway.sendAndWait(command);
        return "Update";
    }

    @DeleteMapping("/{shoppingCartId}")
    public String deleteBook(@PathVariable String shoppingCartId) {
        DeleteShoppingCartCommand command = new DeleteShoppingCartCommand(shoppingCartId);
        commandGateway.sendAndWait(command);
        return "Delete";
    }
}
