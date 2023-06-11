package com.bookstore.commonservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductTypeResponseCommonModel {

    private String id;

    private String name;

    private String description;

    private Date createdAt;

    private Date updatedAt;

}