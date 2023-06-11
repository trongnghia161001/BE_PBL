package com.bookstore.commonservice.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteShoppingCartByUserCommand {

    @TargetAggregateIdentifier
    private Long userId;
}
