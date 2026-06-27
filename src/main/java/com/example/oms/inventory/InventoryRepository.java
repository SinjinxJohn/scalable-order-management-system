package com.example.oms.inventory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory,Long> {
    boolean existsByProductId(Long productId);
    Optional<Inventory> findByProductId(Long productId);

    @Modifying
    @Query(value = """
            UPDATE Inventory i
            SET i.availableQuantity = i.availableQuantity - :quantity,
                i.reservedQuantity = i.reservedQuantity + :quantity
            WHERE i.product.id = :productId
              AND i.availableQuantity >= :quantity
            """)
    int reserveStock(@Param("productId") Long productId, @Param("quantity") int quantity);
}
