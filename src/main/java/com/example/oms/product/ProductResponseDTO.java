package com.example.oms.product;


import com.example.oms.category.CategoryResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ProductResponseDTO {
    private Long id;
    private String name;
    private String description;
    private List<CategoryResponseDTO> categories;
    private Double price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
