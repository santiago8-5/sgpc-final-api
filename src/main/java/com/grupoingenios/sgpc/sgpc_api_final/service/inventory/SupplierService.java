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

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;

    public SupplierService(SupplierRepository supplierRepository, SupplierMapper supplierMapper){
        this.supplierRepository = supplierRepository;
        this.supplierMapper = supplierMapper;
    }

    @Transactional(readOnly = true)
    public List<SupplierResponseDTO> getAllSuppliers(){
        return supplierRepository
                .findAll()
                .stream()
                .map(supplierMapper::toResponseDto)
                .toList();
    }

    @Transactional
    public SupplierResponseDTO createSupplier(SupplierRequestDTO supplierDTO){

        if(supplierRepository.existsByRfcIgnoreCase(supplierDTO.getRfc())){
            throw new BadRequestException(SUPPLIER_EXIST_RFC);
        }
        Supplier supplier = supplierMapper.toEntity(supplierDTO);
        Supplier savedSupplier = supplierRepository.save(supplier);
        return supplierMapper.toResponseDto(savedSupplier);
    }


    @Transactional
    public SupplierResponseDTO updateSupplier(Long id, SupplierRequestDTO supplierDTO){
        Supplier existingSupplier = supplierRepository.findById(id).
                orElseThrow(()-> new ResourceNotFoundException(SUPPLIER_NOT_FOUND));

        validateUniqueName(existingSupplier.getName(), supplierDTO.getName());

        supplierMapper.updatedSupplierFromDTO(supplierDTO, existingSupplier);

        Supplier updateSupplier = supplierRepository.save(existingSupplier);

        return supplierMapper.toResponseDto(updateSupplier);
    }

    @Transactional
    public void deleteSupplier(Long id){

        if(!supplierRepository.existsById(id)){
            throw new BadRequestException(SUPPLIER_NOT_FOUND);
        }

        // Validar si tiene una relación intermedia
        validateRelationships(id);

        // Eliminar relaciones en la tabla intermedia, si no hay relación
        supplierRepository.deleteRelationships(id);

        // Ahora eliminar el Supplier
        supplierRepository.deleteById(id);
    }

    private void validateUniqueName(String currentRfc, String newRfc){
        if(!currentRfc.equalsIgnoreCase(newRfc) && supplierRepository.existsByRfcIgnoreCase(newRfc)){
            throw new BadRequestException(SUPPLIER_EXIST_RFC);
        }
    }

    private void validateRelationships(Long supplierId) {
        if (supplierRepository.hasRelationships(supplierId)) {
            throw new EntityInUseException(ENTITY_IN_USE);
        }
    }

}