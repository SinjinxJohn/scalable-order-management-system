package com.example.oms.category;

import com.example.oms.product.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Category cannot be empty")
    @Column(nullable = false,unique = true)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY,mappedBy = "categories")
    private List<Product> product;

}
