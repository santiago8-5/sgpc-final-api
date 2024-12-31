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

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;
    private final ScheduledActivityRepository scheduledActivityRepository;
    private final WorkRepository workRepository;
    private final ActivityRepository activityRepository;
    private final ScheduledActivityMapper scheduledActivityMapper;



    public ScheduleService(ScheduleRepository scheduleRepository, ScheduleMapper scheduleMapper, ScheduledActivityRepository scheduledActivityRepository,
                           WorkRepository workRepository, ActivityRepository activityRepository, ScheduledActivityMapper scheduledActivityMapper) {
        this.scheduleRepository = scheduleRepository;
        this.scheduleMapper = scheduleMapper;
        this.scheduledActivityRepository = scheduledActivityRepository;
        this.workRepository = workRepository;
        this.activityRepository = activityRepository;
        this.scheduledActivityMapper = scheduledActivityMapper;
    }

    @Transactional(readOnly = true)
    public List<ScheduleResponseDTO> getAllSchedules(){
        return scheduleRepository
                .findAll()
                .stream()
                .map(scheduleMapper::toResponseDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public ScheduleResponseDTO getScheduleById(Long id){
        Schedule schedule = scheduleRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(SCHEDULE_NOT_FOUND));

        return scheduleMapper.toResponseDto(schedule);
    }


    @Transactional
    public ScheduleResponseDTO updateSchedule(long id, ScheduleRequestDTO scheduleRequestDTO){

        // Obtenenmos el cronorgrama existente
        Schedule existingSchedule = scheduleRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(SCHEDULE_NOT_FOUND));

        // Se valida que no exista ese nombre ya en la base de datos
        validateUniqueName(existingSchedule.getName(), scheduleRequestDTO.getName());


        // Actualizamos el cronograma con los datos del mapper
        scheduleMapper.updateScheduleFromDTO(scheduleRequestDTO, existingSchedule);

        // Gudamos el cronograma modificado
        Schedule updatedSchedule = scheduleRepository.save(existingSchedule);

        // Retornamos el DTO response
        return scheduleMapper.toResponseDto(updatedSchedule);

    }


    // Crear scheduledActivity
    @Transactional
    public ScheduledActivityResponseDTO createAndAssignScheduledActivity(Long idSchedule, ScheduledActivityRequestDTO scheduledActivityRequestDTO){
        // Validar fechas
        validateDates(scheduledActivityRequestDTO);

        // Obtenemos el schedule que viene en el request
        Schedule schedule = getScheduleForActivityById(idSchedule);

        // Obtenemos la actividad que viene en el request
        Activity activity = getActivityById(scheduledActivityRequestDTO.getIdActivity());

        // Verificamos si ya existe una relación entre un schedule y una activity
        validateUniqueScheduledActivity(schedule.getIdSchedule(), activity.getIdActivity());

        ScheduledActivity scheduledActivity = scheduledActivityMapper.toEntity(scheduledActivityRequestDTO);
        scheduledActivity.setSchedule(schedule);
        scheduledActivity.setActivity(activity);

        ScheduledActivity savedScheduledActivity = scheduledActivityRepository.save(scheduledActivity);

        return scheduledActivityMapper.toResponseDto(savedScheduledActivity);

    }

    // Obtener un scheduledActivity por id
    @Transactional(readOnly = true)
    public ScheduledActivityResponseDTO getScheduledActivityById(Long scheduleId, Long activityId) {
        // Validar que la actividad programada existe y pertenece al cronograma
        ScheduledActivity scheduledActivity = scheduledActivityRepository.findById(activityId)
                .orElseThrow(() -> new ResourceNotFoundException("Scheduled activity not found"));
        if (!scheduledActivity.getSchedule().getIdSchedule().equals(scheduleId)) {
            throw new BadRequestException("The activity does not belong to the specified schedule.");
        }

        // Mapear a DTO y retornar
        return scheduledActivityMapper.toResponseDto(scheduledActivity);
    }



    // Actualizar ScheduledActivity
    @Transactional
    public ScheduledActivityResponseDTO updateScheduleActivity(Long idSchedule, Long idScheduledActivity, ScheduledActivityRequestDTO scheduledActivityRequestDTO){

        // Validar que la actividad programada existe y pertenece al cronograma
        ScheduledActivity existingScheduledActivity = scheduledActivityRepository
                .findById(idScheduledActivity).orElseThrow(()-> new ResourceNotFoundException("No se encontró este registro"));

        // Obtenemos la actividad que viene en el request
        Activity activity = getActivityById(scheduledActivityRequestDTO.getIdActivity());

        // Validar que la actividad programada existe y pertenece al cronograma
        if (!existingScheduledActivity.getSchedule().getIdSchedule().equals(idSchedule)) {
            throw new BadRequestException("La actividad no pertenece al cronograma especificado.");
        }

        // Validar fechas
        validateDates(scheduledActivityRequestDTO);

        // Actualizamos los datos
        scheduledActivityMapper.updateScheduledActivityFromDTO(scheduledActivityRequestDTO, existingScheduledActivity);

        existingScheduledActivity.setActivity(activity);

        // guadamos
        ScheduledActivity updatedScheduleActivity = scheduledActivityRepository.save(existingScheduledActivity);

        // retornamos la respuesta
        return scheduledActivityMapper.toResponseDto(updatedScheduleActivity);

    }

    // Eliminar ScheduledActivity
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


    private void validateUniqueName(String currentName, String newName){
        if(!currentName.equalsIgnoreCase(newName) && scheduleRepository.existsByNameIgnoreCase(newName)){
            throw new BadRequestException(SCHEDULE_EXIST_NAME);
        }
    }


    private void validateDates(ScheduledActivityRequestDTO dto) {
        if (dto.getEstimatedStartDate().isAfter(dto.getEstimatedEndDate())) {
            throw new BadRequestException("La fecha estimada de inicio no puede ser posterior a la fecha estimada de fin.");
        }
        if (dto.getActualStartDate() != null && dto.getActualEndDate() != null && dto.getActualStartDate().isAfter(dto.getActualEndDate())) {
            throw new BadRequestException("La fecha real de inicio no puede ser posterior a la fecha real de fin.");
        }
    }


    private Schedule getScheduleForActivityById(Long id){
        return scheduleRepository
                .findById(id)
                .orElseThrow(()->new ResourceNotFoundException(SCHEDULE_NOT_FOUND));
    }

    private Activity getActivityById(Long id){
        return activityRepository
                .findById(id)
                .orElseThrow(()->new ResourceNotFoundException(ACTIVITY_NOT_FOUND));
    }

    private void validateUniqueScheduledActivity(Long scheduleId, Long activityId) {
        if (scheduledActivityRepository.existsBySchedule_IdScheduleAndActivity_IdActivity(scheduleId, activityId)) {
            throw new BadRequestException("La actividad ya está asociada al cronograma especificado.");
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

