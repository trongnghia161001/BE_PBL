package com.bookstore.commonservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillCreateStatusEvent {

    private String id;

    private Long userId;

    private String shippingId;

    private Double productCost;

    private Double shippingCost;

    private String paymentMethod;

    private Double totalMoney;

    private String name;

    private String phone;

    private String address;

    private String note;

    private String status;

    private String type;

    private Date createdAt;

    private Date updatedAt;
}
