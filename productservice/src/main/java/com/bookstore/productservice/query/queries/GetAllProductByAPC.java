package com.bookstore.productservice.query.queries;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllProductByAPC {

    private String name;

    private String authorName;

    private String publisherName;

    private Double minPrice;

    private Double maxPrice;


}
