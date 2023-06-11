package com.bookstore.promotionsservice.command.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "promotions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Promotions {

    @Id
    private String id;

    private String promoCode;

    private String description;

    private String discountType;

    private Double discountValue;

    private int status;

    private Date startDate;

    private Date endDate;

    private Date createdAt;

    private Date updatedAt;
}
