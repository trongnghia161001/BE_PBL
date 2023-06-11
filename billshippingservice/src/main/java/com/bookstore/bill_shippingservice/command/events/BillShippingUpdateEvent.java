package com.bookstore.bill_shippingservice.command.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillShippingUpdateEvent {

    private String id;

    private String shipping_id;

    private Date created_at;

    private Date updated_at;
}
