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

    @Transactional(readOnly = true)
    public List<WorkTypeResponseDTO> getAllWorkTypes(){
        return workTypeRepository
                .findAll()
                .stream()
                .map(workTypeMapper::toResponseDTO)
                .toList();
    }

    @Transactional
    public WorkTypeResponseDTO createWorkType(WorkTypeRequestDTO workTypeRequestDTO){
        if(workTypeRepository.existsWorkTypeByNameIgnoreCase(workTypeRequestDTO.getName())){
            throw new BadRequestException(WORK_TYPE_EXIST_NAME);
        }
        WorkType workType = workTypeMapper.toEntity(workTypeRequestDTO);
        WorkType savedWorkType = workTypeRepository.save(workType);

        return workTypeMapper.toResponseDTO(savedWorkType);
    }

    @Transactional
    public WorkTypeResponseDTO updateWorkType(Long id, WorkTypeRequestDTO workTypeRequestDTO){

        WorkType existingWorkType = workTypeRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(WORK_TYPE_NOT_FOUND));

        validateUniqueName(existingWorkType.getName(), workTypeRequestDTO.getName());

        workTypeMapper.updatedWorkTypeFromDTO(workTypeRequestDTO, existingWorkType);

        WorkType updatedWorkType = workTypeRepository.save(existingWorkType);

        return workTypeMapper.toResponseDTO(updatedWorkType);
    }

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

    private void validateUniqueName(String currentName, String newName){
        if(!currentName.equalsIgnoreCase(newName) && workTypeRepository.existsWorkTypeByNameIgnoreCase(newName)){
            throw new BadRequestException(WORK_TYPE_EXIST_NAME);
        }
    }

}