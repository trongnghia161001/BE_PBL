package com.bookstore.promotionsservice.command.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePromotionsCommand {

    @TargetAggregateIdentifier
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
