package com.bookstore.shippingservice.command.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "shipping")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Shipping {

    @Id
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
