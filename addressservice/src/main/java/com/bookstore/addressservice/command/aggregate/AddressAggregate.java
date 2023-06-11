package com.bookstore.addressservice.command.aggregate;

import com.bookstore.addressservice.command.command.CreateAddressCommand;
import com.bookstore.addressservice.command.command.DeleteAddressCommand;
import com.bookstore.addressservice.command.command.UpdateAddressCommand;
import com.bookstore.addressservice.command.events.AddressCreateEvent;
import com.bookstore.addressservice.command.events.AddressDeleteEvent;
import com.bookstore.addressservice.command.events.AddressUpdateEvent;
import com.bookstore.commonservice.command.UpdateAddressByUserCommand;
import com.bookstore.commonservice.event.AddressUpdateByUserEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
public class AddressAggregate {

    @AggregateIdentifier
    private String id;

    private Long userId;

    private String fullName;

    private String phoneNumber;

    private String email;

    private String addressLine1;

    private String addressLine2;

    private String note;

    @CommandHandler 
    public AddressAggregate(CreateAddressCommand command) {
        AddressCreateEvent event = new AddressCreateEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(AddressCreateEvent event) {
        this.id = event.getId();
        this.userId = event.getUserId();
        this.fullName = event.getFullName();
        this.phoneNumber = event.getPhoneNumber();
        this.email = event.getEmail();
        this.addressLine1 = event.getAddressLine1();
        this.addressLine2 = event.getAddressLine2();
        this.note = event.getNote();
    }

    @CommandHandler
    public void handler(UpdateAddressCommand command) {
        AddressUpdateEvent event = new AddressUpdateEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(AddressUpdateEvent event) {
        this.id = event.getId();
        this.userId = event.getUserId();
        this.fullName = event.getFullName();
        this.phoneNumber = event.getPhoneNumber();
        this.email = event.getEmail();
        this.addressLine1 = event.getAddressLine1();
        this.addressLine2 = event.getAddressLine2();
        this.note = event.getNote();
    }

    @CommandHandler
    public void handler(DeleteAddressCommand command) {
        AddressDeleteEvent event = new AddressDeleteEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(AddressDeleteEvent event) {
        this.id = event.getId();
    }

    @CommandHandler
    public void handler(UpdateAddressByUserCommand command) {
        AddressUpdateByUserEvent event = new AddressUpdateByUserEvent();
        event.setId(command.getId());
        event.setAddressLine1(command.getAddressLine1());
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(AddressUpdateByUserEvent event) {
        this.id = event.getId();
        this.addressLine1 = event.getAddressLine1();
    }
}
