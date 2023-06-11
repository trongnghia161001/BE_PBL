package com.bookstore.bill_promotionservice.command.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillPromotionRequestModel {

    private String id;

    private String billId;

    private String promotionId;

    private Double discountAmount;

    private Date createdAt;

    private Date updatedAt;

    }
