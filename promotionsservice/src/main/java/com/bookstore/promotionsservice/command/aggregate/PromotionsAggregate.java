package com.bookstore.promotionsservice.command.aggregate;

import com.bookstore.promotionsservice.command.command.CreatePromotionsCommand;
import com.bookstore.promotionsservice.command.command.DeletePromotionsCommand;
import com.bookstore.promotionsservice.command.command.UpdatePromotionsCommand;
import com.bookstore.promotionsservice.command.events.PromotionsCreateEvent;
import com.bookstore.promotionsservice.command.events.PromotionsDeleteEvent;
import com.bookstore.promotionsservice.command.events.PromotionsUpdateEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Aggregate
public class PromotionsAggregate {

    @AggregateIdentifier
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

    public PromotionsAggregate() {
    }

    @CommandHandler
    public PromotionsAggregate(CreatePromotionsCommand command) {
        PromotionsCreateEvent event = new PromotionsCreateEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(PromotionsCreateEvent event) {
        this.id = event.getId();
        this.promoCode = event.getPromoCode();
        this.description = event.getDescription();
        this.discountType = event.getDiscountType();
        this.discountValue = event.getDiscountValue();
        this.status = event.getStatus();
        this.startDate = event.getStartDate();
        this.endDate = event.getEndDate();
        this.createdAt = event.getCreatedAt();
        this.updatedAt = event.getUpdatedAt();
    }

    @CommandHandler
    public void handler(UpdatePromotionsCommand command) {
        PromotionsUpdateEvent event = new PromotionsUpdateEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(PromotionsUpdateEvent event) {
        this.id = event.getId();
        this.promoCode = event.getPromoCode();
        this.description = event.getDescription();
        this.discountType = event.getDiscountType();
        this.discountValue = event.getDiscountValue();
        this.status = event.getStatus();
        this.startDate = event.getStartDate();
        this.endDate = event.getEndDate();
        this.createdAt = event.getCreatedAt();
        this.updatedAt = event.getUpdatedAt();
    }

    @CommandHandler
    public void handler(DeletePromotionsCommand command) {
        PromotionsDeleteEvent event = new PromotionsDeleteEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(PromotionsDeleteEvent event) {
        this.id = event.getId();
    }
}
