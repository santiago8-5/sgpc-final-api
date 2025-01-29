package com.grupoingenios.sgpc.sgpc_api_final.service.machinery;

import com.grupoingenios.sgpc.sgpc_api_final.dto.machinery.MachineryRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.machinery.MachineryResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.machinery.Machinery;
import com.grupoingenios.sgpc.sgpc_api_final.entity.maintenance.RelatedEntityType;
import com.grupoingenios.sgpc.sgpc_api_final.exception.BadRequestException;
import com.grupoingenios.sgpc.sgpc_api_final.exception.EntityInUseException;
import com.grupoingenios.sgpc.sgpc_api_final.exception.ResourceNotFoundException;
import com.grupoingenios.sgpc.sgpc_api_final.mapper.machinery.MachineryMapper;
import com.grupoingenios.sgpc.sgpc_api_final.repository.machinery.MachineryRepository;
import com.grupoingenios.sgpc.sgpc_api_final.repository.maintenance.MaintenanceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import static com.grupoingenios.sgpc.sgpc_api_final.constants.AppConstant.*;

/**
 * Servicio encargado de gestionar las operaciones relacionadas con la maquinaria.
 * Proporciona métodos para realizar operaciones CRUD sobre la maquinaria,
 * además de validar reglas de negocio como la unicidad del nombre de la maquinaria
 * y la validación de relaciones existentes con el mantenimiento.
 */
@Service
public class MachineryService {

    private final MachineryRepository machineryRepository;
    private final MachineryMapper machineryMapper;
    private final MaintenanceRepository maintenanceRepository;


    public MachineryService(MachineryRepository machineryRepository, MachineryMapper machineryMapper, MaintenanceRepository maintenanceRepository) {
        this.machineryRepository = machineryRepository;
        this.machineryMapper = machineryMapper;
        this.maintenanceRepository = maintenanceRepository;
    }

    /**
     * Obtiene todas las maquinarias registradas en el sistema.
     *
     * @return Lista de maquinarias como DTOs.
     */
    @Transactional(readOnly = true)
    public List<MachineryResponseDTO> getAllMachinery(){
        return machineryRepository
                .findAll()
                .stream()
                .map(machineryMapper::toResponseDto)
                .toList();
    }

    /**
     * Crea una nueva maquinaria en el sistema.
     *
     * @param machineryRequestDTO DTO con los datos de la maquinaria a crear.
     * @return La maquinaria creada como DTO.
     * @throws BadRequestException Si el nombre de la maquinaria ya está en uso.
     */
    @Transactional
    public MachineryResponseDTO createMachinery(MachineryRequestDTO machineryRequestDTO){
        if(machineryRepository.existsByNameIgnoreCase(machineryRequestDTO.getName())){
            throw new BadRequestException(MACHINARY_EXIST_NAME);
        }

        Machinery machinery = machineryMapper.toEntity(machineryRequestDTO);
        Machinery savedMachinery = machineryRepository.save(machinery);

        return machineryMapper.toResponseDto(savedMachinery);
    }


    /**
     * Actualiza los datos de una maquinaria existente en el sistema.
     *
     * @param id El ID de la maquinaria a actualizar.
     * @param machineryRequestDTO DTO con los nuevos datos de la maquinaria.
     * @return La maquinaria actualizada como DTO.
     * @throws ResourceNotFoundException Si la maquinaria no existe.
     * @throws BadRequestException Si el nombre nuevo de la maquinaria ya está en uso.
     */
    @Transactional
    public MachineryResponseDTO updateMachinery(Long id, MachineryRequestDTO machineryRequestDTO){

        Machinery existingMachinery = machineryRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(MACHINARY_NOT_FOUND));

        validateUniqueName(existingMachinery.getName(), machineryRequestDTO.getName());
        machineryMapper.updatedMachineryFromDTO(machineryRequestDTO, existingMachinery);
        Machinery updatedMachinery = machineryRepository.save(existingMachinery);
        return machineryMapper.toResponseDto(updatedMachinery);
    }


    /**
     * Elimina una maquinaria del sistema.
     *
     * @param id El ID de la maquinaria a eliminar.
     * @throws ResourceNotFoundException Si la maquinaria no existe.
     * @throws EntityInUseException Si la maquinaria está siendo utilizada en un mantenimiento.
     */
    @Transactional
    public void deleteMachinery(Long id){
        if(!machineryRepository.existsById(id)){
            throw new ResourceNotFoundException(MACHINARY_NOT_FOUND);
        }
        if(maintenanceRepository.existsByRelatedEntityTypeAndRelatedEntityId(RelatedEntityType.MAQUINARIA, id)){
            throw new EntityInUseException(ENTITY_IN_USE);
        }

        machineryRepository.deleteById(id);
    }

    /**
     * Valida que el nombre de la maquinaria sea único en el sistema.
     *
     * @param currentName El nombre actual de la maquinaria.
     * @param newName El nuevo nombre a validar.
     * @throws BadRequestException Si el nuevo nombre ya está en uso.
     */
    private void validateUniqueName(String currentName, String newName){
        if(!currentName.equalsIgnoreCase(newName) && machineryRepository.existsByNameIgnoreCase(newName)){
            throw new BadRequestException(MACHINARY_EXIST_NAME);
        }
    }

}