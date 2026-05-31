package com.example.oms.inventory;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record InventoryRequestDTO (
        @NotNull
         Long productId,
         @NotNull
         @Min(0)
         Integer availableQuantity,

         @NotNull
         @Min(0)
         Integer reservedQuantity

){
}
