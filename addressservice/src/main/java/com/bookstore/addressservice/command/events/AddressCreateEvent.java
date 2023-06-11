package com.bookstore.addressservice.command.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressCreateEvent {

    private String id;

    private Long userId;

    private String fullName;

    private String phoneNumber;

    private String email;

    private String addressLine1;

    private String addressLine2;

    private String note;
}
