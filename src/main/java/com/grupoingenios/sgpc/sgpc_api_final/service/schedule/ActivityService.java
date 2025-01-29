package com.grupoingenios.sgpc.sgpc_api_final.service.schedule;


import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ActivityRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ActivityResponseDTO;
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

/**
 * Servicio encargado de gestionar las actividades dentro del sistema.
 * Proporciona métodos para realizar operaciones CRUD sobre las actividades,
 * incluyendo la validación de unicidad del nombre y la asociación de las actividades con etapas.
 */
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

    /**
     * Obtiene todas las actividades registradas en el sistema.
     *
     * @return Lista de actividades como DTOs.
     */
    @Transactional(readOnly = true)
    public List<ActivityResponseDTO> getAllActivity(){
        return activityRepository
                .findAll()
                .stream()
                .map(activityMapper::toResponseDto)
                .toList();
    }


    /**
     * Crea una nueva actividad en el sistema.
     *
     * @param activityRequestDTO DTO con los datos de la actividad a crear.
     * @return La actividad creada como DTO.
     * @throws BadRequestException Si la actividad con el mismo nombre ya existe.
     */
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


    /**
     * Actualiza una actividad existente en el sistema.
     *
     * @param id El ID de la actividad a actualizar.
     * @param activityRequestDTO DTO con los nuevos datos de la actividad.
     * @return La actividad actualizada como DTO.
     * @throws ResourceNotFoundException Si la actividad no existe.
     * @throws BadRequestException Si el nombre de la actividad ya está en uso.
     */
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


    /**
     * Elimina una actividad del sistema.
     *
     * @param id El ID de la actividad a eliminar.
     * @throws ResourceNotFoundException Si la actividad no existe.
     * @throws EntityInUseException Si la actividad está asociada a una actividad programada.
     */
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

    /**
     * Obtiene la etapa asociada al ID proporcionado.
     *
     * @param id El ID de la etapa.
     * @return La etapa correspondiente.
     * @throws ResourceNotFoundException Si la etapa no existe.
     */
    private Stage getStageById(Long id){
        return stageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(STAGE_NOT_FOUND));
    }


    /**
     * Valida que el nombre de la actividad no esté en uso.
     *
     * @param currentName El nombre actual de la actividad.
     * @param newName El nuevo nombre de la actividad.
     * @throws BadRequestException Si el nuevo nombre ya está en uso.
     */
    private void validateUniqueName(String currentName, String newName){
        if(!currentName.equalsIgnoreCase(newName) && activityRepository.existsByNameIgnoreCase(newName)){
            throw new BadRequestException(ACTIVITY_EXIST_NAME);
        }
    }

}
