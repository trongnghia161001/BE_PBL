package com.bookstore.ratingsservice.query.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingsResponseModel {

    private String id;

    private String user_id;

    private String product_id;

    private int number;

    private int status;

    private String content;

    private Date created_at;

    private Date updated_at;
}
