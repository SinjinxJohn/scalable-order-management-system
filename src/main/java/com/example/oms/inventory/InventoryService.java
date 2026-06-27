package com.example.oms.inventory;


import com.example.oms.exceptions.ResourceAlreadyExistsException;
import com.example.oms.exceptions.ResourceNotFoundException;
import com.example.oms.product.Product;
import com.example.oms.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;

    public InventoryResponseDTO createInventory(InventoryRequestDTO inventoryRequestDTO){

        Product product = productRepository.findById(inventoryRequestDTO.productId())
                .orElseThrow(()-> new ResourceNotFoundException("Product with this id does not exist"));
        if(inventoryRepository.existsByProductId(product.getId())){
            throw new ResourceAlreadyExistsException("Inventory with this product Id already exists");
        }
        Inventory inventory = Inventory.builder()
                    .product(product)
                    .availableQuantity(inventoryRequestDTO.availableQuantity())
                    .reservedQuantity(inventoryRequestDTO.reservedQuantity())
                    .build();
        Inventory savedInventory = inventoryRepository.save(inventory);

        return new InventoryResponseDTO(savedInventory.getId(),savedInventory.getProduct().getId(), savedInventory.getAvailableQuantity(), savedInventory.getReservedQuantity(),
                savedInventory.getCreatedAt(),savedInventory.getUpdatedAt());

    }

    public InventoryResponseDTO getInventory(Long Id){
        Inventory inventory = inventoryRepository.findById(Id).orElseThrow(()-> new ResourceNotFoundException("Inventory with this id does not exist"));
        return new InventoryResponseDTO(inventory.getId(),inventory.getProduct().getId(), inventory.getAvailableQuantity(), inventory.getReservedQuantity(),
                inventory.getCreatedAt(),inventory.getUpdatedAt());
    }

    public Page<InventoryResponseDTO> getAllInventory(Pageable pageable){
        Page<Inventory> inventories = inventoryRepository.findAll(pageable);
        return inventories.map(inventory -> new InventoryResponseDTO(inventory.getId(),inventory.getProduct().getId(), inventory.getAvailableQuantity(), inventory.getReservedQuantity(),
                inventory.getCreatedAt(),inventory.getUpdatedAt()));

    }



}
