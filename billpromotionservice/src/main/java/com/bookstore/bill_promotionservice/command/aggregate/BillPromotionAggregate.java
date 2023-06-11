package com.bookstore.bill_promotionservice.command.aggregate;

import com.bookstore.bill_promotionservice.command.command.CreateBillPromotionCommand;
import com.bookstore.bill_promotionservice.command.command.DeleteBillPromotionCommand;
import com.bookstore.bill_promotionservice.command.command.UpdateBillPromotionCommand;
import com.bookstore.bill_promotionservice.command.events.BillPromotionCreateEvent;
import com.bookstore.bill_promotionservice.command.events.BillPromotionDeleteEvent;
import com.bookstore.bill_promotionservice.command.events.BillPromotionUpdateEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Aggregate
public class BillPromotionAggregate {

    @AggregateIdentifier
    private String id;

    private String billId;

    private String promotionId;

    private Double discountAmount;

    private Date createdAt;

    private Date updatedAt;

    public BillPromotionAggregate() {
    }

    @CommandHandler
    public BillPromotionAggregate(CreateBillPromotionCommand command) {
        BillPromotionCreateEvent event = new BillPromotionCreateEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(BillPromotionCreateEvent event) {
        this.id = event.getId();
        this.billId = event.getBillId();
        this.promotionId = event.getPromotionId();
        this.discountAmount = event.getDiscountAmount();
        this.createdAt = event.getCreatedAt();
        this.updatedAt = event.getUpdatedAt();
    }

    @CommandHandler
    public void handler(UpdateBillPromotionCommand command) {
        BillPromotionUpdateEvent event = new BillPromotionUpdateEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(BillPromotionUpdateEvent event) {
        this.id = event.getId();
        this.billId = event.getBillId();
        this.promotionId = event.getPromotionId();
        this.discountAmount = event.getDiscountAmount();
        this.createdAt = event.getCreatedAt();
        this.updatedAt = event.getUpdatedAt();
    }

    @CommandHandler
    public void handler(DeleteBillPromotionCommand command) {
        BillPromotionDeleteEvent event = new BillPromotionDeleteEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(BillPromotionDeleteEvent event) {
        this.id = event.getId();
    }
}
