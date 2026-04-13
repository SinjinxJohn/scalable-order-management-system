package com.example.oms.product;

import com.example.oms.category.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Product name cannot be null ")
    @Column(nullable = false)
    private String name;
    @NotBlank(message = "Product description cannot be null")
    @Column(nullable = false)
    private String description;

    @NotBlank(message = "Product price cannot be null")
    @Column(nullable = false)
    private Double price;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "product_category", joinColumns = @JoinColumn(name = "product_id"),inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categoryList;


    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;


}
