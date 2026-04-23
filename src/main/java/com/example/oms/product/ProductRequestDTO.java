package com.example.oms.product;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductRequestDTO {
    private String name;
    private String description;
    private List<Long> categories;
    private Double price;
}
