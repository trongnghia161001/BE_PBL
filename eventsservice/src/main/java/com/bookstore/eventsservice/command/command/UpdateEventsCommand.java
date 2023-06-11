package com.bookstore.eventsservice.command.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventsCommand {

    @TargetAggregateIdentifier
    private String id;

    private String admin_id;

    private String name;

    private String banner;

    private String link;

    private int position_1;

    private int position_2;

    private int position_3;

    private int position_4;

    private int position_5;

    private int position_6;

    private Date created_at;

    private Date updated_at;
}
