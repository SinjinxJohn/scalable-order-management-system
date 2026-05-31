package com.example.oms.inventory;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record InventoryResponseDTO (
        Long Id,
        Long productId,
        Integer availableQuantity,
        Integer reservedQuantity,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

){}
