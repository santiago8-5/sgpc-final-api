package com.grupoingenios.sgpc.sgpc_api_final.service.inventory;

import com.grupoingenios.sgpc.sgpc_api_final.dto.inventory.InventoryRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.inventory.InventoryResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.inventory.Inventory;
import com.grupoingenios.sgpc.sgpc_api_final.entity.inventory.Supplier;
import com.grupoingenios.sgpc.sgpc_api_final.exception.EntityInUseException;
import com.grupoingenios.sgpc.sgpc_api_final.exception.ResourceNotFoundException;
import com.grupoingenios.sgpc.sgpc_api_final.mapper.inventory.InventoryMapper;
import com.grupoingenios.sgpc.sgpc_api_final.repository.inventory.InventoryRepository;
import com.grupoingenios.sgpc.sgpc_api_final.repository.inventory.SupplierRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.grupoingenios.sgpc.sgpc_api_final.constants.AppConstant.*;

/**
 * Servicio encargado de gestionar las operaciones relacionadas con los inventarios.
 * Proporciona métodos para realizar operaciones CRUD sobre los inventarios,
 * y valida las relaciones de los inventarios con los proveedores y otras entidades.
 */
@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;
    private final SupplierRepository supplierRepository;

    public InventoryService(InventoryRepository inventoryRepository, InventoryMapper inventoryMapper, SupplierRepository supplierRepository){
        this.inventoryRepository = inventoryRepository;
        this.inventoryMapper = inventoryMapper;
        this.supplierRepository = supplierRepository;
    }

    /**
     * Obtiene todos los inventarios en el sistema.
     *
     * @return Lista de inventarios como DTOs.
     */
    @Transactional(readOnly = true)
    public List<InventoryResponseDTO> getAllInventories() {
        return inventoryRepository
                .findAll()
                .stream()
                .map(inventoryMapper::toResponseDTO)
                .toList();
    }

    /**
     * Crea un nuevo inventario en el sistema y asigna un proveedor.
     *
     * @param inventoryRequest DTO con los datos del inventario a crear.
     * @return El inventario creado como DTO.
     */
    @Transactional
    public InventoryResponseDTO createInventory(InventoryRequestDTO inventoryRequest) {

        Supplier supplier = getSupplierById(inventoryRequest.getSupplierId());

        Inventory inventory = inventoryMapper.toEntity(inventoryRequest);

        inventory.getSuppliers().add(supplier);

        Inventory savedInventory = inventoryRepository.save(inventory);

        return inventoryMapper.toResponseDTO(savedInventory);

    }

    /**
     * Actualiza un inventario existente en el sistema.
     *
     * @param id El ID del inventario a actualizar.
     * @param inventoryRequest DTO con los nuevos datos del inventario.
     * @return El inventario actualizado como DTO.
     */
    @Transactional
    public InventoryResponseDTO updateInventory(Long id, InventoryRequestDTO inventoryRequest) {

        Inventory inventoryExisting = inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(INPUT_NOT_FOUND));

        inventoryMapper.updatedInventoryFromDTO(inventoryRequest, inventoryExisting);

        if (inventoryRequest.getSupplierId() != null) {
            Supplier newSupplier = getSupplierById(inventoryRequest.getSupplierId());
            inventoryExisting.getSuppliers().clear();
            inventoryExisting.getSuppliers().add(newSupplier);
        }

        Inventory updatedInventory = inventoryRepository.save(inventoryExisting);

        return inventoryMapper.toResponseDTO(updatedInventory);
    }


    /**
     * Elimina un inventario del sistema por su ID.
     *
     * @param id El ID del inventario a eliminar.
     * @throws ResourceNotFoundException Si el inventario no existe.
     * @throws EntityInUseException Si el inventario tiene relaciones asociadas.
     */
    @Transactional
    public void deleteInventoryById(Long id){

        if (!inventoryRepository.existsById(id)) {
            throw new ResourceNotFoundException(INPUT_NOT_FOUND );
        }

        validateRelationships(id);

        inventoryRepository.deleteRelationships(id);

        inventoryRepository.deleteById(id);
    }


    /**
     * Obtiene un proveedor por su ID.
     *
     * @param id El ID del proveedor.
     * @return El proveedor correspondiente.
     * @throws ResourceNotFoundException Si el proveedor no existe.
     */
    public Supplier getSupplierById(Long id){
        return supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(SUPPLIER_NOT_FOUND));
    }

    /**
     * Valida si un inventario tiene relaciones asociadas antes de eliminarlo.
     *
     * @param inventoryId El ID del inventario a verificar.
     * @throws EntityInUseException Si el inventario tiene relaciones activas.
     */
    private void validateRelationships(Long inventoryId) {
        if (inventoryRepository.hasRelationships(inventoryId)) {
            throw new EntityInUseException(ENTITY_IN_USE);
        }
    }

}
