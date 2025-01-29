package com.grupoingenios.sgpc.sgpc_api_final.service.work;

import com.grupoingenios.sgpc.sgpc_api_final.dto.work.WorkTypeRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.work.WorkTypeResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.work.WorkType;
import com.grupoingenios.sgpc.sgpc_api_final.exception.BadRequestException;
import com.grupoingenios.sgpc.sgpc_api_final.exception.EntityInUseException;
import com.grupoingenios.sgpc.sgpc_api_final.exception.ResourceNotFoundException;
import com.grupoingenios.sgpc.sgpc_api_final.mapper.work.WorkTypeMapper;
import com.grupoingenios.sgpc.sgpc_api_final.repository.work.WorkRepository;
import com.grupoingenios.sgpc.sgpc_api_final.repository.work.WorkTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.grupoingenios.sgpc.sgpc_api_final.constants.AppConstant.*;

/**
 * Servicio encargado de gestionar las operaciones relacionadas con los tipos de trabajos.
 * Proporciona métodos para realizar operaciones CRUD sobre los tipos de trabajos,
 * además de validar reglas de negocio como la unicidad del nombre del tipo de trabajo.
 */
@Service
public class WorkTypeService {

    private final WorkTypeRepository workTypeRepository;
    private final WorkTypeMapper workTypeMapper;
    private final WorkRepository workRepository;

    public WorkTypeService(WorkTypeRepository workTypeRepository, WorkTypeMapper workTypeMapper, WorkRepository workRepository) {
        this.workTypeRepository = workTypeRepository;
        this.workTypeMapper = workTypeMapper;
        this.workRepository = workRepository;
    }

    /**
     * Obtiene todos los tipos de trabajos en el sistema.
     *
     * @return Lista de tipos de trabajos como DTOs.
     */
    @Transactional(readOnly = true)
    public List<WorkTypeResponseDTO> getAllWorkTypes(){
        return workTypeRepository
                .findAll()
                .stream()
                .map(workTypeMapper::toResponseDTO)
                .toList();
    }


    /**
     * Crea un nuevo tipo de trabajo en el sistema.
     *
     * @param workTypeRequestDTO DTO con los datos del tipo de trabajo a crear.
     * @return El tipo de trabajo creado como DTO.
     * @throws BadRequestException Si el nombre del tipo de trabajo ya está en uso.
     */
    @Transactional
    public WorkTypeResponseDTO createWorkType(WorkTypeRequestDTO workTypeRequestDTO){
        if(workTypeRepository.existsWorkTypeByNameIgnoreCase(workTypeRequestDTO.getName())){
            throw new BadRequestException(WORK_TYPE_EXIST_NAME);
        }
        WorkType workType = workTypeMapper.toEntity(workTypeRequestDTO);
        WorkType savedWorkType = workTypeRepository.save(workType);

        return workTypeMapper.toResponseDTO(savedWorkType);
    }


    /**
     * Actualiza un tipo de trabajo existente en el sistema.
     *
     * @param id El ID del tipo de trabajo a actualizar.
     * @param workTypeRequestDTO DTO con los nuevos datos del tipo de trabajo.
     * @return El tipo de trabajo actualizado como DTO.
     * @throws ResourceNotFoundException Si el tipo de trabajo no existe.
     * @throws BadRequestException Si el nuevo nombre ya está en uso.
     */
    @Transactional
    public WorkTypeResponseDTO updateWorkType(Long id, WorkTypeRequestDTO workTypeRequestDTO){

        WorkType existingWorkType = workTypeRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(WORK_TYPE_NOT_FOUND));

        validateUniqueName(existingWorkType.getName(), workTypeRequestDTO.getName());

        workTypeMapper.updatedWorkTypeFromDTO(workTypeRequestDTO, existingWorkType);

        WorkType updatedWorkType = workTypeRepository.save(existingWorkType);

        return workTypeMapper.toResponseDTO(updatedWorkType);
    }

    /**
     * Elimina un tipo de trabajo del sistema.
     *
     * @param id El ID del tipo de trabajo a eliminar.
     * @throws ResourceNotFoundException Si el tipo de trabajo no existe.
     * @throws EntityInUseException Si el tipo de trabajo tiene trabajos asociados.
     */
    @Transactional
    public void deleteWorkType(Long id){
        if(!workTypeRepository.existsById(id)){
            throw new ResourceNotFoundException(WORK_TYPE_NOT_FOUND);
        }

        if(workRepository.existsByWorkType_IdWorkType(id)){
            throw new EntityInUseException(ENTITY_IN_USE);
        }

        workTypeRepository.deleteById(id);
    }


    /**
     * Valida que el nuevo nombre del tipo de trabajo no esté en uso.
     *
     * @param currentName El nombre actual del tipo de trabajo.
     * @param newName El nuevo nombre del tipo de trabajo.
     * @throws BadRequestException Si el nuevo nombre ya está en uso.
     */
    private void validateUniqueName(String currentName, String newName){
        if(!currentName.equalsIgnoreCase(newName) && workTypeRepository.existsWorkTypeByNameIgnoreCase(newName)){
            throw new BadRequestException(WORK_TYPE_EXIST_NAME);
        }
    }

}