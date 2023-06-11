package com.bookstore.shippingservice.command.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShippingUpdateEvent {
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