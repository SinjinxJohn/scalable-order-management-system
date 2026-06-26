package com.example.oms.orders;

import com.example.oms.product.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderItemResponseDTO {
    Long id;
    Long productId;
    BigDecimal unitPrice;
    Integer quantity;
    Long orderId;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
