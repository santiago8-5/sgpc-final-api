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


    @Transactional(readOnly = true)
    public List<InventoryResponseDTO> getAllInventories() {
        return inventoryRepository
                .findAll()
                .stream()
                .map(inventoryMapper::toResponseDTO)
                .toList();
    }

    @Transactional
    public InventoryResponseDTO createInventory(InventoryRequestDTO inventoryRequest) {

        // Obtener el proveedor especificado en supplierId.
        Supplier supplier = getSupplierById(inventoryRequest.getSupplierId());

        // Transformar el dto a entidad.
        Inventory inventory = inventoryMapper.toEntity(inventoryRequest);

        // Añadir el supplier al inventory.
        inventory.getSuppliers().add(supplier);

        // Guardar el inventario con el supplier.
        Inventory savedInventory = inventoryRepository.save(inventory);

        // Retornar la respuesta.
        return inventoryMapper.toResponseDTO(savedInventory);

    }

    @Transactional
    public InventoryResponseDTO updateInventory(Long id, InventoryRequestDTO inventoryRequest) {

        // Recuperamos el inventory existente
        Inventory inventoryExisting = inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(INPUT_NOT_FOUND));

        // Actualizar los campos básicos
        inventoryMapper.updatedInventoryFromDTO(inventoryRequest, inventoryExisting);

        // Verificar si hay un nuevo supplierId y actualizar el proveedor si es necesario
        if (inventoryRequest.getSupplierId() != null) {
            Supplier newSupplier = getSupplierById(inventoryRequest.getSupplierId());
            inventoryExisting.getSuppliers().clear();
            inventoryExisting.getSuppliers().add(newSupplier);
        }

        // Actualizar un inventario
        Inventory updatedInventory = inventoryRepository.save(inventoryExisting);

        // Retornar la respuesta
        return inventoryMapper.toResponseDTO(updatedInventory);
    }

    @Transactional
    public void deleteInventoryById(Long id){

        // Comprobar si existe el inventory
        if (!inventoryRepository.existsById(id)) {
            throw new ResourceNotFoundException(INPUT_NOT_FOUND );
        }

        // Validar si tiene una relación intermedia
        validateRelationships(id);

        // Eliminar relaciones en la tabla intermedia, si no hay relación
        inventoryRepository.deleteRelationships(id);

        // Eliminar la entidad
        inventoryRepository.deleteById(id);
    }


    public Supplier getSupplierById(Long id){
        return supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(SUPPLIER_NOT_FOUND));
    }

    private void validateRelationships(Long inventoryId) {
        if (inventoryRepository.hasRelationships(inventoryId)) {
            throw new EntityInUseException(ENTITY_IN_USE);
        }
    }

}
