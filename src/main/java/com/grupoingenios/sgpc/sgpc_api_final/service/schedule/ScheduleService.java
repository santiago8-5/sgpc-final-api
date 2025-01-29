package com.grupoingenios.sgpc.sgpc_api_final.service.schedule;

import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ScheduleRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ScheduleResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ScheduledActivityRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ScheduledActivityResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.schedule.Activity;
import com.grupoingenios.sgpc.sgpc_api_final.entity.schedule.Schedule;
import com.grupoingenios.sgpc.sgpc_api_final.entity.schedule.ScheduledActivity;
import com.grupoingenios.sgpc.sgpc_api_final.exception.BadRequestException;
import com.grupoingenios.sgpc.sgpc_api_final.exception.EntityInUseException;
import com.grupoingenios.sgpc.sgpc_api_final.exception.ResourceNotFoundException;
import com.grupoingenios.sgpc.sgpc_api_final.mapper.schedule.ScheduleMapper;
import com.grupoingenios.sgpc.sgpc_api_final.mapper.schedule.ScheduledActivityMapper;
import com.grupoingenios.sgpc.sgpc_api_final.repository.schedule.ActivityRepository;
import com.grupoingenios.sgpc.sgpc_api_final.repository.schedule.ScheduleRepository;
import com.grupoingenios.sgpc.sgpc_api_final.repository.schedule.ScheduledActivityRepository;
import com.grupoingenios.sgpc.sgpc_api_final.repository.work.WorkRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.grupoingenios.sgpc.sgpc_api_final.constants.AppConstant.*;


/**
 * Servicio encargado de gestionar las operaciones relacionadas con los cronogramas y las actividades programadas.
 * Proporciona métodos para realizar operaciones CRUD sobre los cronogramas, las actividades programadas y sus asignaciones.
 * Incluye validaciones sobre la relación de actividades con cronogramas y fechas válidas.
 */
@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;
    private final ScheduledActivityRepository scheduledActivityRepository;
    private final WorkRepository workRepository;
    private final ActivityRepository activityRepository;
    private final ScheduledActivityMapper scheduledActivityMapper;



    /**
     * Constructor que inicializa el servicio con sus dependencias necesarias.
     *
     * @param scheduleRepository Repositorio de cronogramas.
     * @param scheduleMapper Mapper para convertir entre entidades y DTOs de cronogramas.
     * @param scheduledActivityRepository Repositorio de actividades programadas.
     * @param workRepository Repositorio de trabajos relacionados con los cronogramas.
     * @param activityRepository Repositorio de actividades.
     * @param scheduledActivityMapper Mapper para convertir entre entidades y DTOs de actividades programadas.
     */
    public ScheduleService(ScheduleRepository scheduleRepository, ScheduleMapper scheduleMapper, ScheduledActivityRepository scheduledActivityRepository,
                           WorkRepository workRepository, ActivityRepository activityRepository, ScheduledActivityMapper scheduledActivityMapper) {
        this.scheduleRepository = scheduleRepository;
        this.scheduleMapper = scheduleMapper;
        this.scheduledActivityRepository = scheduledActivityRepository;
        this.workRepository = workRepository;
        this.activityRepository = activityRepository;
        this.scheduledActivityMapper = scheduledActivityMapper;
    }

    /**
     * Obtiene todos los cronogramas en el sistema.
     *
     * @return Lista de cronogramas como DTOs.
     */
    @Transactional(readOnly = true)
    public List<ScheduleResponseDTO> getAllSchedules(){
        return scheduleRepository
                .findAll()
                .stream()
                .map(scheduleMapper::toResponseDto)
                .toList();
    }


    /**
     * Obtiene los detalles de un cronograma por su ID.
     *
     * @param id ID del cronograma a buscar.
     * @return El cronograma correspondiente como DTO.
     * @throws ResourceNotFoundException Si no se encuentra el cronograma.
     */
    @Transactional(readOnly = true)
    public ScheduleResponseDTO getScheduleById(Long id){
        Schedule schedule = scheduleRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(SCHEDULE_NOT_FOUND));

        return scheduleMapper.toResponseDto(schedule);
    }


    /**
     * Actualiza un cronograma existente.
     *
     * @param id ID del cronograma a actualizar.
     * @param scheduleRequestDTO DTO con los nuevos datos del cronograma.
     * @return El cronograma actualizado como DTO.
     * @throws ResourceNotFoundException Si no se encuentra el cronograma.
     * @throws BadRequestException Si el nuevo nombre del cronograma ya está en uso.
     */
    @Transactional
    public ScheduleResponseDTO updateSchedule(long id, ScheduleRequestDTO scheduleRequestDTO){

        Schedule existingSchedule = scheduleRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(SCHEDULE_NOT_FOUND));

        validateUniqueName(existingSchedule.getName(), scheduleRequestDTO.getName());

        scheduleMapper.updateScheduleFromDTO(scheduleRequestDTO, existingSchedule);

        Schedule updatedSchedule = scheduleRepository.save(existingSchedule);

        return scheduleMapper.toResponseDto(updatedSchedule);

    }


    /**
     * Crea una nueva actividad programada y la asigna a un cronograma.
     *
     * @param idSchedule ID del cronograma al que se asignará la actividad programada.
     * @param scheduledActivityRequestDTO DTO con los datos de la actividad programada.
     * @return La actividad programada creada como DTO.
     * @throws BadRequestException Si las fechas son inválidas o si la relación ya existe.
     */
    @Transactional
    public ScheduledActivityResponseDTO createAndAssignScheduledActivity(Long idSchedule, ScheduledActivityRequestDTO scheduledActivityRequestDTO){

        validateDates(scheduledActivityRequestDTO);

        Schedule schedule = getScheduleForActivityById(idSchedule);

        Activity activity = getActivityById(scheduledActivityRequestDTO.getIdActivity());

        validateUniqueScheduledActivity(schedule.getIdSchedule(), activity.getIdActivity(), null);

        ScheduledActivity scheduledActivity = scheduledActivityMapper.toEntity(scheduledActivityRequestDTO);
        scheduledActivity.setSchedule(schedule);
        scheduledActivity.setActivity(activity);

        ScheduledActivity savedScheduledActivity = scheduledActivityRepository.save(scheduledActivity);

        return scheduledActivityMapper.toResponseDto(savedScheduledActivity);

    }


    /**
     * Obtiene los detalles de una actividad programada por su ID y el ID del cronograma.
     *
     * @param scheduleId ID del cronograma.
     * @param activityId ID de la actividad programada.
     * @return La actividad programada correspondiente como DTO.
     * @throws ResourceNotFoundException Si no se encuentra la actividad programada o no pertenece al cronograma especificado.
     */
    @Transactional(readOnly = true)
    public ScheduledActivityResponseDTO getScheduledActivityById(Long scheduleId, Long activityId) {

        ScheduledActivity scheduledActivity = scheduledActivityRepository.findById(activityId)
                .orElseThrow(() -> new ResourceNotFoundException("Actividad programada no encontrada"));
        if (!scheduledActivity.getSchedule().getIdSchedule().equals(scheduleId)) {
            throw new BadRequestException("La actividad no pertenece al cronograma expecificado.");
        }

        return scheduledActivityMapper.toResponseDto(scheduledActivity);
    }


    /**
     * Actualiza una actividad programada existente.
     *
     * @param idSchedule ID del cronograma al que pertenece la actividad programada.
     * @param idScheduledActivity ID de la actividad programada a actualizar.
     * @param scheduledActivityRequestDTO DTO con los nuevos datos de la actividad programada.
     * @return La actividad programada actualizada como DTO.
     * @throws ResourceNotFoundException Si no se encuentra la actividad programada o el cronograma.
     * @throws BadRequestException Si las fechas son inválidas o si la relación ya existe.
     */
    @Transactional
    public ScheduledActivityResponseDTO updateScheduleActivity(Long idSchedule, Long idScheduledActivity, ScheduledActivityRequestDTO scheduledActivityRequestDTO){

        validateDates(scheduledActivityRequestDTO);

        Schedule schedule = getScheduleForActivityById(idSchedule);

        ScheduledActivity existingScheduledActivity = scheduledActivityRepository
                .findById(idScheduledActivity).orElseThrow(()-> new ResourceNotFoundException("No se encontró este registro"));

        Activity activity = getActivityById(scheduledActivityRequestDTO.getIdActivity());

        validateUniqueScheduledActivity(schedule.getIdSchedule(), activity.getIdActivity(), idScheduledActivity);


        if (!existingScheduledActivity.getSchedule().getIdSchedule().equals(idSchedule)) {
            throw new BadRequestException("La actividad no pertenece al cronograma especificado.");
        }

        scheduledActivityMapper.updateScheduledActivityFromDTO(scheduledActivityRequestDTO, existingScheduledActivity);

        existingScheduledActivity.setActivity(activity);

        ScheduledActivity updatedScheduleActivity = scheduledActivityRepository.save(existingScheduledActivity);

        return scheduledActivityMapper.toResponseDto(updatedScheduleActivity);

    }


    /**
     * Elimina una actividad programada.
     *
     * @param idSchedule ID del cronograma al que pertenece la actividad programada.
     * @param idScheduledActivity ID de la actividad programada a eliminar.
     * @throws ResourceNotFoundException Si no se encuentra la actividad programada.
     */
    @Transactional
    public void deleteScheduledActivity(Long idSchedule, Long idScheduledActivity){

        if(!scheduledActivityRepository.existsById(idScheduledActivity)){
            throw new ResourceNotFoundException("Detalles de actividad no encontrada");
        }
        /*
        validateNoWorkerAssignments(Long id);
         */
        scheduledActivityRepository.deleteById(idScheduledActivity);
    }


    /**
     * Elimina un cronograma.
     *
     * @param id ID del cronograma a eliminar.
     * @throws ResourceNotFoundException Si no se encuentra el cronograma.
     * @throws EntityInUseException Si el cronograma tiene actividades programadas o trabajos asociados.
     */
    @Transactional
    public void deleteSchedule(Long id){

        if(!scheduleRepository.existsById(id)){
            throw new ResourceNotFoundException(SCHEDULE_NOT_FOUND);
        }

        if(scheduledActivityRepository.existsBySchedule_IdSchedule(id) || workRepository.existsBySchedule_IdSchedule(id)){
            throw new EntityInUseException(ENTITY_IN_USE);
        }

        scheduleRepository.deleteById(id);
    }


    /**
     * Valida que el nombre del cronograma no exista ya en la base de datos.
     *
     * @param currentName El nombre actual del cronograma.
     * @param newName El nuevo nombre del cronograma.
     * @throws BadRequestException Si el nuevo nombre ya está en uso.
     */
    private void validateUniqueName(String currentName, String newName){
        if(!currentName.equalsIgnoreCase(newName) && scheduleRepository.existsByNameIgnoreCase(newName)){
            throw new BadRequestException(SCHEDULE_EXIST_NAME);
        }
    }


    /**
     * Valida que las fechas de inicio y fin sean correctas.
     *
     * @param dto DTO con las fechas de la actividad programada.
     * @throws BadRequestException Si las fechas no son válidas.
     */
    private void validateDates(ScheduledActivityRequestDTO dto) {
        if (dto.getEstimatedStartDate().isAfter(dto.getEstimatedEndDate())) {
            throw new BadRequestException("La fecha estimada de inicio no puede ser posterior a la fecha estimada de fin.");
        }
        if (dto.getActualStartDate() != null && dto.getActualEndDate() != null && dto.getActualStartDate().isAfter(dto.getActualEndDate())) {
            throw new BadRequestException("La fecha real de inicio no puede ser posterior a la fecha real de fin.");
        }
    }



    /**
     * Obtiene el cronograma para una actividad programada por su ID.
     *
     * @param id ID del cronograma.
     * @return El cronograma correspondiente.
     * @throws ResourceNotFoundException Si no se encuentra el cronograma.
     */
    private Schedule getScheduleForActivityById(Long id){
        return scheduleRepository
                .findById(id)
                .orElseThrow(()->new ResourceNotFoundException(SCHEDULE_NOT_FOUND));
    }



    /**
     * Obtiene la actividad por su ID.
     *
     * @param id ID de la actividad.
     * @return La actividad correspondiente.
     * @throws ResourceNotFoundException Si no se encuentra la actividad.
     */
    private Activity getActivityById(Long id){
        return activityRepository
                .findById(id)
                .orElseThrow(()->new ResourceNotFoundException(ACTIVITY_NOT_FOUND));
    }


    /**
     * Valida que no exista una actividad programada ya asociada al cronograma.
     *
     * @param scheduleId ID del cronograma.
     * @param activityId ID de la actividad.
     * @param excludedScheduledActivityId ID de la actividad programada a excluir de la validación.
     * @throws BadRequestException Si la actividad ya está asociada al cronograma.
     */
    private void validateUniqueScheduledActivity(Long scheduleId, Long activityId, Long excludedScheduledActivityId) {
        if (excludedScheduledActivityId == null) {
            boolean exists = scheduledActivityRepository.existsBySchedule_IdScheduleAndActivity_IdActivity(scheduleId, activityId);
            if (exists) {
                throw new BadRequestException("La actividad ya está asociada al cronograma especificado.");
            }
        } else {
            boolean exists = scheduledActivityRepository.existsBySchedule_IdScheduleAndActivity_IdActivityAndScheduledActivityIdNot(
                    scheduleId, activityId, excludedScheduledActivityId);
            if (exists) {
                throw new BadRequestException("La actividad ya está asociada al cronograma especificado.");
            }
        }
    }

    /*
    private void validateNoWorkerAssignments(Long scheduledActivityId) {
        if (scheduledActivityRepository.hasWorkerAssignments(scheduledActivityId)) {
            throw new BadRequestException("No se puede eliminar la actividad porque tiene trabajadores asignados.");
        }
    }

     */




}

