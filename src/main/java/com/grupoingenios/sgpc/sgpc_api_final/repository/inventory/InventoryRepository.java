package com.grupoingenios.sgpc.sgpc_api_final.repository.inventory;

import com.grupoingenios.sgpc.sgpc_api_final.entity.inventory.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    @Query(value = "SELECT IF(COUNT(*) > 0, 'true', 'false')  FROM inventory_supplier WHERE inventory_id = :inventoryId", nativeQuery = true)
    boolean hasRelationships(@Param("inventoryId") Long inventoryId);
    
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM inventory_supplier WHERE inventory_id = :inventoryId", nativeQuery = true)
    void deleteRelationships(@Param("inventoryId") Long inventoryId);

}
