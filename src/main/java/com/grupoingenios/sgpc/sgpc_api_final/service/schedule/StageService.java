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

/**
 * Servicio encargado de gestionar las operaciones relacionadas con las etapas en el sistema.
 * Proporciona métodos para realizar operaciones CRUD sobre las etapas, además de validar reglas de negocio como la unicidad del nombre de la etapa.
 */
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


    /**
     * Obtiene todas las etapas en el sistema.
     *
     * @return Lista de etapas como DTOs.
     */
    @Transactional(readOnly = true)
    public List<StageResponseDTO> getAllStage(){
        return stageRepository
                .findAll()
                .stream()
                .map(stageMapper::toResponseDto)
                .toList();
    }


    /**
     * Crea una nueva etapa en el sistema.
     *
     * @param stageRequestDTO DTO con los datos de la etapa a crear.
     * @return La etapa creada como DTO.
     * @throws BadRequestException Si el nombre de la etapa ya está en uso.
     */
    @Transactional
    public StageResponseDTO createStage(StageRequestDTO stageRequestDTO){

        if(stageRepository.existsByNameIgnoreCase(stageRequestDTO.getName())){
            throw new BadRequestException(STAGE_NOT_FOUND);
        }

        Stage stage = stageMapper.toEntity(stageRequestDTO);
        Stage savedStage = stageRepository.save(stage);

        return stageMapper.toResponseDto(savedStage);

    }


    /**
     * Actualiza una etapa existente en el sistema.
     *
     * @param id ID de la etapa a actualizar.
     * @param stageRequestDTO DTO con los nuevos datos de la etapa.
     * @return La etapa actualizada como DTO.
     * @throws ResourceNotFoundException Si la etapa no existe.
     * @throws BadRequestException Si el nuevo nombre de la etapa ya está en uso.
     */
    @Transactional
    public StageResponseDTO updateStage(Long id, StageRequestDTO stageRequestDTO){

        Stage existingStage = stageRepository
                .findById(id).orElseThrow(()-> new ResourceNotFoundException(STAGE_NOT_FOUND));

        validateUniqueName(existingStage.getName(), stageRequestDTO.getName());

        stageMapper.updateStageFromDTO(stageRequestDTO, existingStage);

        Stage updatedStage = stageRepository.save(existingStage);

        return stageMapper.toResponseDto(updatedStage);

    }


    /**
     * Elimina una etapa del sistema.
     *
     * @param id ID de la etapa a eliminar.
     * @throws ResourceNotFoundException Si la etapa no existe.
     * @throws EntityInUseException Si la etapa tiene actividades asociadas.
     */
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

    /**
     * Valida que el nombre de la etapa no esté ya en uso.
     *
     * @param currentName El nombre actual de la etapa.
     * @param newName El nuevo nombre de la etapa.
     * @throws BadRequestException Si el nuevo nombre ya está en uso.
     */
    private void validateUniqueName(String currentName, String newName){
        if(!currentName.equalsIgnoreCase(newName) && stageRepository.existsByNameIgnoreCase(newName)){
            throw new BadRequestException(STAGE_EXIST_NAME);
        }
    }

}
