package com.bookstore.bill_promotionservice.command.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBillPromotionCommand {

    @TargetAggregateIdentifier
    private String id;

    private String billId;

    private String promotionId;

    private Double discountAmount;

    private Date createdAt;

    private Date updatedAt;
}
