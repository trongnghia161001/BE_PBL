package com.bookstore.shippingservice.command.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateShippingCommand {

    @TargetAggregateIdentifier
    private String id;

    private String userId;

    private String billId;

    private Date shippingDate;

    private Date estimatedDeliveryDate;

    private Date actualDeliveryDate;

    private String shippingMethod;

    private String trackingNumber;

    private String carrierId;

    private String status;

    private Double shipmentCost;

    private String note;

    private Date createdAt;

    private Date updatedAt;
}
