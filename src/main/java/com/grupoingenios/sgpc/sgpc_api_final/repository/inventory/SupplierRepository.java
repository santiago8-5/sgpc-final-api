package com.grupoingenios.sgpc.sgpc_api_final.repository.inventory;

import com.grupoingenios.sgpc.sgpc_api_final.entity.inventory.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    boolean existsByRfcIgnoreCase(String rfc);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM inventory_supplier WHERE supplier_id = :supplierId", nativeQuery = true)
    void deleteRelationships(@Param("supplierId") Long supplierId);


    @Query(value = "SELECT IF(COUNT(*) > 0, 'true', 'false')  FROM inventory_supplier WHERE supplier_id = :supplierId", nativeQuery = true)
    boolean hasRelationships(@Param("supplierId") Long supplierId);


    /*
    @Query(value = "SELECT COUNT(*) > 0 FROM inventory_supplier WHERE supplier_id = :supplierId", nativeQuery = true)
    boolean hasRelationships(@Param("supplierId") Long supplierId);
     */

}

