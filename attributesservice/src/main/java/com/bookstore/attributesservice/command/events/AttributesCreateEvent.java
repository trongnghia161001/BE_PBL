package com.bookstore.attributesservice.command.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttributesCreateEvent {

    private String id;

    private String name;

    private String slug;

    private int type;

    private String categoryId;

    private Date created_at;

    private Date updated_at;
}
