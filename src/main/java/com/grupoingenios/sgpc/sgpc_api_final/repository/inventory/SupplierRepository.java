package com.grupoingenios.sgpc.sgpc_api_final.repository.inventory;

import com.grupoingenios.sgpc.sgpc_api_final.entity.inventory.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repositorio para la entidad `Supplier`.
 * Proporciona métodos para realizar operaciones CRUD sobre los proveedores,
 * así como consultas personalizadas para verificar relaciones y eliminar relaciones en la base de datos.
 */
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    /**
     * Verifica si existe un proveedor con el RFC proporcionado.
     *
     * @param rfc El RFC del proveedor.
     * @return `true` si existe un proveedor con el RFC proporcionado, de lo contrario `false`.
     */
    boolean existsByRfcIgnoreCase(String rfc);

    /**
     * Elimina las relaciones entre un proveedor y los inventarios en la tabla `inventory_supplier`.
     *
     * @param supplierId El ID del proveedor cuya relación con los inventarios se desea eliminar.
     */
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM inventory_supplier WHERE supplier_id = :supplierId", nativeQuery = true)
    void deleteRelationships(@Param("supplierId") Long supplierId);


    /**
     * Verifica si el proveedor con el ID proporcionado tiene relaciones en la tabla `inventory_supplier`.
     *
     * @param supplierId El ID del proveedor.
     * @return `true` si el proveedor tiene relaciones en la tabla `inventory_supplier`, de lo contrario `false`.
     */
    @Query(value = "SELECT IF(COUNT(*) > 0, 'true', 'false')  FROM inventory_supplier WHERE supplier_id = :supplierId", nativeQuery = true)
    boolean hasRelationships(@Param("supplierId") Long supplierId);


    /*
    @Query(value = "SELECT COUNT(*) > 0 FROM inventory_supplier WHERE supplier_id = :supplierId", nativeQuery = true)
    boolean hasRelationships(@Param("supplierId") Long supplierId);
     */

}

