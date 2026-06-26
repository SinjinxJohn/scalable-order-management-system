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
    @Query("""
            UPDATE inventory i
            SET i.available_quantity = i.available_quantity - :quantity,
                i.reserved_quantity = i.reserved_quantity + :quantity
            WHERE i.product_id = :productId
              AND i.available_quantity >= :quantity;
            """)
    int reserveStock(@Param("productId") Long productId, @Param("quantity") int quantity);
}
