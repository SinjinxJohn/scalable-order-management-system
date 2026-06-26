package com.example.oms.orders;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderResponseDTO {

    Long id;
    String orderStatus;
    List<OrderItem> orderItem;
    BigDecimal totalAmount;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
