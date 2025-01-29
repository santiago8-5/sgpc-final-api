package com.grupoingenios.sgpc.sgpc_api_final.repository.inventory;

import com.grupoingenios.sgpc.sgpc_api_final.entity.inventory.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repositorio para la entidad `Inventory`.
 * Proporciona métodos para realizar operaciones CRUD sobre los inventarios,
 * así como consultas personalizadas para verificar relaciones y eliminar relaciones en la base de datos.
 */
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    /**
     * Verifica si el inventario con el ID proporcionado tiene relaciones en la tabla `inventory_supplier`.
     *
     * @param inventoryId El ID del inventario.
     * @return `true` si el inventario tiene relaciones en la tabla `inventory_supplier`, de lo contrario `false`.
     */
    @Query(value = "SELECT IF(COUNT(*) > 0, 'true', 'false')  FROM inventory_supplier WHERE inventory_id = :inventoryId", nativeQuery = true)
    boolean hasRelationships(@Param("inventoryId") Long inventoryId);


    /**
     * Elimina las relaciones entre un inventario y los proveedores en la tabla `inventory_supplier`.
     *
     * @param inventoryId El ID del inventario cuyo proveedor se desea eliminar.
     */
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM inventory_supplier WHERE inventory_id = :inventoryId", nativeQuery = true)
    void deleteRelationships(@Param("inventoryId") Long inventoryId);

}
