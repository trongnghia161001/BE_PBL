package com.bookstore.commonservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromotionResponseCommonModel {

    private String id;

    private String promoCode;

    private String description;

    private String discountType;

    private Double discountValue;

    private int status;

    private Date startDate;

    private Date endDate;

    private Date createdAt;

    private Date updatedAt;

}
