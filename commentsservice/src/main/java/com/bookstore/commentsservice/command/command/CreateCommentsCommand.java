package com.bookstore.commentsservice.command.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCommentsCommand {

    @TargetAggregateIdentifier
    private String id;

    private String name;

    private String email;

    private String content;

    private String productId;

    private String adminId;

    private Long userId;

    private int like;

    private int disk_like;

    private Date created_at;

    private Date updated_at;
}
