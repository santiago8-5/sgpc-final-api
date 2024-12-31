package com.grupoingenios.sgpc.sgpc_api_final.service.schedule;


import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.StageRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.StageResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.schedule.Stage;
import com.grupoingenios.sgpc.sgpc_api_final.exception.BadRequestException;
import com.grupoingenios.sgpc.sgpc_api_final.exception.EntityInUseException;
import com.grupoingenios.sgpc.sgpc_api_final.exception.ResourceNotFoundException;
import com.grupoingenios.sgpc.sgpc_api_final.mapper.schedule.StageMapper;
import com.grupoingenios.sgpc.sgpc_api_final.repository.schedule.ActivityRepository;
import com.grupoingenios.sgpc.sgpc_api_final.repository.schedule.StageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.grupoingenios.sgpc.sgpc_api_final.constants.AppConstant.*;

@Service
public class StageService {

    private final StageRepository stageRepository;
    private final StageMapper stageMapper;
    private final ActivityRepository activityRepository;


    public StageService(StageRepository stageRepository, StageMapper activityMapper, ActivityRepository activityRepository) {
        this.stageRepository = stageRepository;
        this.stageMapper = activityMapper;
        this.activityRepository = activityRepository;
    }


    @Transactional(readOnly = true)
    public List<StageResponseDTO> getAllStage(){
        return stageRepository
                .findAll()
                .stream()
                .map(stageMapper::toResponseDto)
                .toList();
    }

    @Transactional
    public StageResponseDTO createStage(StageRequestDTO stageRequestDTO){

        if(stageRepository.existsByNameIgnoreCase(stageRequestDTO.getName())){
            throw new BadRequestException(STAGE_NOT_FOUND);
        }

        Stage stage = stageMapper.toEntity(stageRequestDTO);
        Stage savedStage = stageRepository.save(stage);

        return stageMapper.toResponseDto(savedStage);

    }

    @Transactional
    public StageResponseDTO updateStage(Long id, StageRequestDTO stageRequestDTO){

        Stage existingStage = stageRepository
                .findById(id).orElseThrow(()-> new ResourceNotFoundException(STAGE_NOT_FOUND));

        validateUniqueName(existingStage.getName(), stageRequestDTO.getName());

        stageMapper.updateStageFromDTO(stageRequestDTO, existingStage);

        Stage updatedStage = stageRepository.save(existingStage);

        return stageMapper.toResponseDto(updatedStage);

    }

    @Transactional
    public void deleteStage(long id){
        if(!stageRepository.existsById(id)){
            throw new ResourceNotFoundException(STAGE_NOT_FOUND);
        }

        if(activityRepository.existsByStage_IdStage(id)){
            throw new EntityInUseException(ENTITY_IN_USE);
        }

        stageRepository.deleteById(id);
    }


    private void validateUniqueName(String currentName, String newName){
        if(!currentName.equalsIgnoreCase(newName) && stageRepository.existsByNameIgnoreCase(newName)){
            throw new BadRequestException(STAGE_EXIST_NAME);
        }
    }

}
