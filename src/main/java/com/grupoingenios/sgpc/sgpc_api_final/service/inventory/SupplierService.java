package com.grupoingenios.sgpc.sgpc_api_final.service.inventory;

import com.grupoingenios.sgpc.sgpc_api_final.dto.inventory.SupplierRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.inventory.SupplierResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.inventory.Supplier;
import com.grupoingenios.sgpc.sgpc_api_final.exception.BadRequestException;
import com.grupoingenios.sgpc.sgpc_api_final.exception.EntityInUseException;
import com.grupoingenios.sgpc.sgpc_api_final.exception.ResourceNotFoundException;
import com.grupoingenios.sgpc.sgpc_api_final.mapper.inventory.SupplierMapper;
import com.grupoingenios.sgpc.sgpc_api_final.repository.inventory.SupplierRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static com.grupoingenios.sgpc.sgpc_api_final.constants.AppConstant.*;

/**
 * Servicio encargado de gestionar las operaciones relacionadas con los proveedores.
 * Proporciona métodos para realizar operaciones CRUD sobre los proveedores,
 * y valida reglas de negocio como la unicidad del RFC del proveedor y la validación de relaciones existentes.
 */
@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;

    public SupplierService(SupplierRepository supplierRepository, SupplierMapper supplierMapper){
        this.supplierRepository = supplierRepository;
        this.supplierMapper = supplierMapper;
    }

    /**
     * Obtiene todos los proveedores en el sistema.
     *
     * @return Lista de proveedores como DTOs.
     */
    @Transactional(readOnly = true)
    public List<SupplierResponseDTO> getAllSuppliers(){
        return supplierRepository
                .findAll()
                .stream()
                .map(supplierMapper::toResponseDto)
                .toList();
    }

    /**
     * Crea un nuevo proveedor en el sistema.
     *
     * @param supplierDTO DTO con los datos del proveedor a crear.
     * @return El proveedor creado como DTO.
     * @throws BadRequestException Si el RFC del proveedor ya está en uso.
     */
    @Transactional
    public SupplierResponseDTO createSupplier(SupplierRequestDTO supplierDTO){

        if(supplierRepository.existsByRfcIgnoreCase(supplierDTO.getRfc())){
            throw new BadRequestException(SUPPLIER_EXIST_RFC);
        }
        Supplier supplier = supplierMapper.toEntity(supplierDTO);
        Supplier savedSupplier = supplierRepository.save(supplier);
        return supplierMapper.toResponseDto(savedSupplier);
    }


    /**
     * Actualiza un proveedor existente en el sistema.
     *
     * @param id El ID del proveedor a actualizar.
     * @param supplierDTO DTO con los nuevos datos del proveedor.
     * @return El proveedor actualizado como DTO.
     * @throws ResourceNotFoundException Si el proveedor no existe.
     * @throws BadRequestException Si el RFC nuevo ya está en uso.
     */
    @Transactional
    public SupplierResponseDTO updateSupplier(Long id, SupplierRequestDTO supplierDTO){
        Supplier existingSupplier = supplierRepository.findById(id).
                orElseThrow(()-> new ResourceNotFoundException(SUPPLIER_NOT_FOUND));

        validateUniqueName(existingSupplier.getName(), supplierDTO.getName());

        supplierMapper.updatedSupplierFromDTO(supplierDTO, existingSupplier);

        Supplier updateSupplier = supplierRepository.save(existingSupplier);

        return supplierMapper.toResponseDto(updateSupplier);
    }

    /**
     * Elimina un proveedor del sistema.
     *
     * @param id El ID del proveedor a eliminar.
     * @throws BadRequestException Si el proveedor no existe.
     * @throws EntityInUseException Si el proveedor tiene relaciones asociadas.
     */
    @Transactional
    public void deleteSupplier(Long id){

        if(!supplierRepository.existsById(id)){
            throw new BadRequestException(SUPPLIER_NOT_FOUND);
        }

        validateRelationships(id);
        supplierRepository.deleteRelationships(id);
        supplierRepository.deleteById(id);
    }

    /**
     * Valida que el RFC de un proveedor no esté en uso.
     *
     * @param currentRfc El RFC actual del proveedor.
     * @param newRfc El nuevo RFC a validar.
     * @throws BadRequestException Si el nuevo RFC ya está en uso.
     */
    private void validateUniqueName(String currentRfc, String newRfc){
        if(!currentRfc.equalsIgnoreCase(newRfc) && supplierRepository.existsByRfcIgnoreCase(newRfc)){
            throw new BadRequestException(SUPPLIER_EXIST_RFC);
        }
    }

    /**
     * Valida si un proveedor tiene relaciones activas antes de ser eliminado.
     *
     * @param supplierId El ID del proveedor a verificar.
     * @throws EntityInUseException Si el proveedor tiene relaciones activas.
     */
    private void validateRelationships(Long supplierId) {
        if (supplierRepository.hasRelationships(supplierId)) {
            throw new EntityInUseException(ENTITY_IN_USE);
        }
    }

}