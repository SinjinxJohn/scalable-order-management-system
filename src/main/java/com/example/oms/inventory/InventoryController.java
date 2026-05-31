package com.example.oms.inventory;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @PostMapping
    ResponseEntity<InventoryResponseDTO> createProduct(@RequestBody @Valid InventoryRequestDTO inventoryRequestDTO){
        return new ResponseEntity<>(inventoryService.createInventory(inventoryRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    ResponseEntity<InventoryResponseDTO> getInventoryById(@PathVariable("id") Long Id){
        return ResponseEntity.ok(inventoryService.getInventory(Id));
    }

    @GetMapping
    ResponseEntity<Page<InventoryResponseDTO>> getAllInventories(Pageable pageable){
        return ResponseEntity.ok(inventoryService.getAllInventory(pageable));
    }

}
