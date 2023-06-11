package com.bookstore.manage_warehouseservice.command.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManageWarehouseCreateEvent {

    private String id;

    private String warehouse_id;

    private String product_id;

    private int amount;

    private Date created_at;

    private Date updated_at;
}
