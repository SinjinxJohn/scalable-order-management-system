package com.example.oms.orders;

import com.example.oms.product.Product;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;


@Getter
public class OrderItemRequestDTO {
    @NotNull
     Long productId;
     @Min(value = 1)
     Integer quantity;
}
