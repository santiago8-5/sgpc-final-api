package com.grupoingenios.sgpc.sgpc_api_final.service.schedule;


import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ActivityRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ActivityResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.employee.Position;
import com.grupoingenios.sgpc.sgpc_api_final.entity.schedule.Activity;
import com.grupoingenios.sgpc.sgpc_api_final.entity.schedule.Stage;
import com.grupoingenios.sgpc.sgpc_api_final.exception.BadRequestException;
import com.grupoingenios.sgpc.sgpc_api_final.exception.EntityInUseException;
import com.grupoingenios.sgpc.sgpc_api_final.exception.ResourceNotFoundException;
import com.grupoingenios.sgpc.sgpc_api_final.mapper.schedule.ActivityMapper;
import com.grupoingenios.sgpc.sgpc_api_final.repository.schedule.ActivityRepository;
import com.grupoingenios.sgpc.sgpc_api_final.repository.schedule.ScheduledActivityRepository;
import com.grupoingenios.sgpc.sgpc_api_final.repository.schedule.StageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import static com.grupoingenios.sgpc.sgpc_api_final.constants.AppConstant.*;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final ActivityMapper activityMapper;
    private final StageRepository stageRepository;
    private final ScheduledActivityRepository scheduledActivityRepository;

    public ActivityService(ActivityRepository activityRepository, ActivityMapper activityMapper, StageRepository stageRepository, ScheduledActivityRepository scheduledActivityRepository) {
        this.activityRepository = activityRepository;
        this.activityMapper = activityMapper;
        this.stageRepository = stageRepository;
        this.scheduledActivityRepository = scheduledActivityRepository;
    }


    @Transactional(readOnly = true)
    public List<ActivityResponseDTO> getAllActivity(){
        return activityRepository
                .findAll()
                .stream()
                .map(activityMapper::toResponseDto)
                .toList();
    }

    @Transactional
    public ActivityResponseDTO createActivity(ActivityRequestDTO activityRequestDTO){

        if(activityRepository.existsByNameIgnoreCase(activityRequestDTO.getName())){
            throw new BadRequestException(ACTIVITY_NOT_FOUND);
        }

        Stage stage = getStageById(activityRequestDTO.getIdStage());

        Activity activity = activityMapper.toEntity(activityRequestDTO);
        activity.setStage(stage);

        Activity savedActivity = activityRepository.save(activity);

        return activityMapper.toResponseDto(savedActivity);

    }

    @Transactional
    public ActivityResponseDTO updateActivity(Long id, ActivityRequestDTO activityRequestDTO){

        Activity existingActivity = activityRepository
                .findById(id).orElseThrow(()-> new ResourceNotFoundException(ACTIVITY_NOT_FOUND));

        Stage stage = getStageById(activityRequestDTO.getIdStage());


        validateUniqueName(existingActivity.getName(), activityRequestDTO.getName());

        activityMapper.updateActivityFromDTO(activityRequestDTO, existingActivity);
        existingActivity.setStage(stage);

        Activity updatedActivity = activityRepository.save(existingActivity);

        return activityMapper.toResponseDto(updatedActivity);

    }

    @Transactional
    public void deleteActivity(long id){
        if(!activityRepository.existsById(id)){
            throw new ResourceNotFoundException(ACTIVITY_NOT_FOUND);
        }

        if(scheduledActivityRepository.existsByActivity_IdActivity(id)){
            throw new EntityInUseException(ENTITY_IN_USE);
        }


        activityRepository.deleteById(id);
    }

    private Stage getStageById(Long id){
        return stageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(STAGE_NOT_FOUND));
    }


    private void validateUniqueName(String currentName, String newName){
        if(!currentName.equalsIgnoreCase(newName) && activityRepository.existsByNameIgnoreCase(newName)){
            throw new BadRequestException(ACTIVITY_EXIST_NAME);
        }
    }

}
