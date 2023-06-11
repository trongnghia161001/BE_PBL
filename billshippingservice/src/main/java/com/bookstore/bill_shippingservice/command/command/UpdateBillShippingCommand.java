package com.bookstore.bill_shippingservice.command.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBillShippingCommand {

    @TargetAggregateIdentifier
    private String id;

    private String shipping_id;

    private Date created_at;

    private Date updated_at;
}
