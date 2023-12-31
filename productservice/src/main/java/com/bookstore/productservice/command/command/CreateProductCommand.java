package com.bookstore.productservice.command.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductCommand {

    @TargetAggregateIdentifier
    private String id;

    private String name;

    private String slug;

    private Double price;

    private Double priceEntry;

    private String publisherId;

    private String authorId;

    private String productTypeId;

    private String categories;

    private Double sale;

    private LocalDate expirationDate;

    private String avatar;

    private int view;

    private int hot;

    private int expiration;

    private int active;

    private String description;

    private String content;

    private int reviewTotal;

    private int reviewStar;

    private int ageReview;

    private int number;

    private int importGoods;

    private int numberImport;

    private String resistant;

    private String energy;

    private String countryCode;

    private int soldQuantity;

    private LocalDate publishedDate;

    private String dataField;

    private String binding;

    private LocalDate createdAt;

    private LocalDate updatedAt;
}
