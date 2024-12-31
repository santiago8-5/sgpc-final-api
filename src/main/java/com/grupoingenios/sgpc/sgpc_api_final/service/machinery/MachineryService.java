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

    @Transactional(readOnly = true)
    public List<MachineryResponseDTO> getAllMachinery(){
        return machineryRepository
                .findAll()
                .stream()
                .map(machineryMapper::toResponseDto)
                .toList();
    }

    @Transactional
    public MachineryResponseDTO createMachinery(MachineryRequestDTO machineryRequestDTO){
        if(machineryRepository.existsByNameIgnoreCase(machineryRequestDTO.getName())){
            throw new BadRequestException(MACHINARY_EXIST_NAME);
        }

        Machinery machinery = machineryMapper.toEntity(machineryRequestDTO);
        Machinery savedMachinery = machineryRepository.save(machinery);

        return machineryMapper.toResponseDto(savedMachinery);
    }

    @Transactional
    public MachineryResponseDTO updateMachinery(Long id, MachineryRequestDTO machineryRequestDTO){

        // Recuperando la maquinaria existente
        Machinery existingMachinery = machineryRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(MACHINARY_NOT_FOUND));

        // Validando que haya un unico nombre.
        validateUniqueName(existingMachinery.getName(), machineryRequestDTO.getName());

        // Actualizar los campos
        machineryMapper.updatedMachineryFromDTO(machineryRequestDTO, existingMachinery);

        // Guardar los campos actualizados
        Machinery updatedMachinery = machineryRepository.save(existingMachinery);

        // Retornar la respuesta
        return machineryMapper.toResponseDto(updatedMachinery);

    }

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

    private void validateUniqueName(String currentName, String newName){
        if(!currentName.equalsIgnoreCase(newName) && machineryRepository.existsByNameIgnoreCase(newName)){
            throw new BadRequestException(MACHINARY_EXIST_NAME);
        }
    }

}