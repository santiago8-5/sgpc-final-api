package com.grupoingenios.sgpc.sgpc_api_final.service.schedule;

import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ActivityAssignmentRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ActivityAssignmentResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ScheduledActivityResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.employee.Employee;
import com.grupoingenios.sgpc.sgpc_api_final.entity.schedule.ActivityAssignment;
import com.grupoingenios.sgpc.sgpc_api_final.entity.schedule.ScheduledActivity;
import com.grupoingenios.sgpc.sgpc_api_final.exception.BadRequestException;
import com.grupoingenios.sgpc.sgpc_api_final.exception.ResourceNotFoundException;
import com.grupoingenios.sgpc.sgpc_api_final.mapper.schedule.ActivityAssignmentMapper;
import com.grupoingenios.sgpc.sgpc_api_final.mapper.schedule.ScheduledActivityMapper;
import com.grupoingenios.sgpc.sgpc_api_final.repository.employee.EmployeeRepository;
import com.grupoingenios.sgpc.sgpc_api_final.repository.schedule.ActivityAssignmentRepository;
import com.grupoingenios.sgpc.sgpc_api_final.repository.schedule.ScheduledActivityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import static com.grupoingenios.sgpc.sgpc_api_final.constants.AppConstant.EMPLOYEE_NOT_FOUND;

/**
 * Servicio encargado de gestionar las actividades programadas y sus asignaciones de empleados.
 * Este servicio incluye la creación, actualización, eliminación y validación de asignaciones de actividades programadas
 * a empleados, así como la verificación de responsabilidades dentro de las actividades.
 */
@Service
public class ScheduledActivityService {

    private final ScheduledActivityRepository scheduledActivityRepository;
    private final ScheduledActivityMapper scheduledActivityMapper;
    private final ActivityAssignmentRepository activityAssignmentRepository;
    private final ActivityAssignmentMapper activityAssignmentMapper;
    private final EmployeeRepository employeeRepository;


    /**
     * Constructor que inicializa el servicio con sus dependencias necesarias.
     *
     * @param scheduledActivityRepository Repositorio de actividades programadas.
     * @param scheduledActivityMapper Mapper para convertir entre entidades y DTOs de actividades programadas.
     * @param activityAssignmentRepository Repositorio de asignaciones de actividades.
     * @param activityAssignmentMapper Mapper para convertir entre entidades y DTOs de asignaciones.
     * @param employeeRepository Repositorio de empleados.
     */
    public ScheduledActivityService(ScheduledActivityRepository scheduledActivityRepository, ScheduledActivityMapper scheduledActivityMapper,
                                    ActivityAssignmentRepository activityAssignmentRepository, ActivityAssignmentMapper activityAssignmentMapper, EmployeeRepository employeeRepository) {
        this.scheduledActivityRepository = scheduledActivityRepository;
        this.scheduledActivityMapper = scheduledActivityMapper;
        this.activityAssignmentRepository = activityAssignmentRepository;
        this.activityAssignmentMapper = activityAssignmentMapper;
        this.employeeRepository = employeeRepository;
    }

    /**
     * Obtiene todas las actividades programadas en el sistema.
     *
     * @return Lista de actividades programadas como DTOs.
     */
    @Transactional(readOnly = true)
    public List<ScheduledActivityResponseDTO> getAllScheduleActivity(){
        return scheduledActivityRepository
                .findAll()
                .stream()
                .map(scheduledActivityMapper::toResponseDto)
                .toList();
    }


    /**
     * Verifica si una actividad programada tiene al menos un responsable.
     *
     * @param scheduledActivityId ID de la actividad programada.
     * @return `true` si tiene un responsable, `false` en caso contrario.
     */
    public boolean checkIfActivityHasResponsible(Long scheduledActivityId) {
        return activityAssignmentRepository.existsByScheduledActivity_ScheduledActivityIdAndResponsibleTrue(scheduledActivityId);
    }


    /**
     * Obtiene todas las asignaciones de una actividad programada.
     *
     * @param scheduledActivityId ID de la actividad programada.
     * @return Lista de asignaciones de actividades como DTOs.
     */
    @Transactional(readOnly = true)
    public List<ActivityAssignmentResponseDTO> getAssignmentsByScheduledActivityId(Long scheduledActivityId) {
        return activityAssignmentRepository
                .findByScheduledActivity_ScheduledActivityId(scheduledActivityId)
                .stream()
                .map(activityAssignmentMapper::toResponseDto)
                .toList();
    }


    /**
     * Retorna los detalles de las actividades programadas asociadas a un cronograma específico.
     *
     * @param scheduleId ID del cronograma.
     * @return Lista de actividades programadas como DTOs.
     */
    @Transactional(readOnly = true)
    public List<ScheduledActivityResponseDTO> getScheduledActivitiesBySchedule(Long scheduleId){
        return scheduledActivityRepository
                .findByScheduleId(scheduleId);
    }



    /**
     * Crea una asignación de un empleado a una actividad programada.
     *
     * @param scheduledActivityId ID de la actividad programada.
     * @param activityAssignmentRequestDTO DTO con los datos de la asignación.
     * @return La asignación creada como DTO.
     * @throws BadRequestException Si el empleado ya está asignado a la actividad programada.
     */
    @Transactional
    public ActivityAssignmentResponseDTO createActivityAssignment(Long scheduledActivityId, ActivityAssignmentRequestDTO activityAssignmentRequestDTO){

        validateUniqueAssignment(scheduledActivityId, activityAssignmentRequestDTO.getEmployeeId());

        ScheduledActivity scheduledActivity = getScheduledActivityById(scheduledActivityId);

        Employee employee = getEmployeeById(activityAssignmentRequestDTO.getEmployeeId());


        ActivityAssignment activityAssignment = activityAssignmentMapper.toEntity(activityAssignmentRequestDTO);

        activityAssignment.setScheduledActivity(scheduledActivity);
        activityAssignment.setEmployee(employee);

        ActivityAssignment savedActivityAssignment = activityAssignmentRepository.save(activityAssignment);

        validateAtLeastOneResponsible(scheduledActivity.getScheduledActivityId());

        return activityAssignmentMapper.toResponseDto(savedActivityAssignment);

    }


    /**
     * Actualiza una asignación de un empleado a una actividad programada.
     *
     * @param scheduledActivityId ID de la actividad programada.
     * @param activityAssignmentId ID de la asignación a actualizar.
     * @param activityAssignmentRequestDTO DTO con los nuevos datos de la asignación.
     * @return La asignación actualizada como DTO.
     * @throws ResourceNotFoundException Si la asignación o actividad no existe.
     * @throws BadRequestException Si hay un problema con la duplicidad o responsable.
     */
    @Transactional
    public ActivityAssignmentResponseDTO updateActivityAssignment(Long scheduledActivityId, Long activityAssignmentId, ActivityAssignmentRequestDTO activityAssignmentRequestDTO) {

        ActivityAssignment existingActivityAssignment = activityAssignmentRepository
                .findById(activityAssignmentId).orElseThrow(() -> new ResourceNotFoundException("Registro no encontrado"));

        if (!activityAssignmentRepository.existsByScheduledActivity_ScheduledActivityIdAndActivityAssignmentId(scheduledActivityId, activityAssignmentId)) {
            throw new ResourceNotFoundException("La asignación no pertenece a la actividad programada especificada o no existe.");
        }

        if (!existingActivityAssignment.getEmployee().getIdEmployee().equals(activityAssignmentRequestDTO.getEmployeeId())) {
            validateUniqueAssignment(scheduledActivityId, activityAssignmentRequestDTO.getEmployeeId());
        }

        if (existingActivityAssignment.isResponsible() && !activityAssignmentRequestDTO.isResponsible()) {
            validateAtLeastOneResponsible(existingActivityAssignment.getScheduledActivity().getScheduledActivityId());
        }

        if (!existingActivityAssignment.getEmployee().getIdEmployee().equals(activityAssignmentRequestDTO.getEmployeeId())) {
            Employee newEmployee = getEmployeeById(activityAssignmentRequestDTO.getEmployeeId());
            existingActivityAssignment.setEmployee(newEmployee);
        }

        activityAssignmentMapper.updateActivityAssignmentFromDTO(activityAssignmentRequestDTO, existingActivityAssignment);

        ActivityAssignment updatedActivityAssignment = activityAssignmentRepository.save(existingActivityAssignment);

        return activityAssignmentMapper.toResponseDto(updatedActivityAssignment);
    }


    /**
     * Elimina una asignación de un empleado a una actividad programada.
     *
     * @param scheduledActivityId ID de la actividad programada.
     * @param activityAssignmentId ID de la asignación a eliminar.
     * @throws ResourceNotFoundException Si la asignación no existe.
     */
    @Transactional
    public void deleteActivityAssignment(Long scheduledActivityId, Long activityAssignmentId){

        if (!activityAssignmentRepository.existsByScheduledActivity_ScheduledActivityIdAndActivityAssignmentId(scheduledActivityId, activityAssignmentId)) {
            throw new ResourceNotFoundException("La asignación no pertenece a la actividad programada especificada o no existe.");
        }

        activityAssignmentRepository.deleteById(activityAssignmentId);

    }


    /**
     * Valida que un empleado no esté asignado más de una vez a la misma actividad programada.
     *
     * @param scheduledActivityId ID de la actividad programada.
     * @param employeeId ID del empleado.
     * @throws BadRequestException Si el empleado ya está asignado a la actividad.
     */
    private void validateUniqueAssignment(Long scheduledActivityId, Long employeeId) {
        if (activityAssignmentRepository.existsByScheduledActivity_ScheduledActivityIdAndEmployee_IdEmployee(scheduledActivityId, employeeId)) {
            throw new BadRequestException("El empleado ya está asignado a esta actividad programada.");
        }
    }


    /**
     * Valida que haya al menos un responsable en una actividad programada.
     *
     * @param scheduledActivityId ID de la actividad programada.
     * @throws BadRequestException Si no hay responsables asignados.
     */
    private void validateAtLeastOneResponsible(Long scheduledActivityId) {
        boolean hasResponsible = activityAssignmentRepository.existsByScheduledActivity_ScheduledActivityIdAndResponsibleTrue(scheduledActivityId);
        if (!hasResponsible) {
            throw new BadRequestException("Debe haber al menos un empleado responsable para esta actividad programada.");
        }
    }

    /**
     * Recupera una actividad programada por su ID.
     *
     * @param id ID de la actividad programada.
     * @return La actividad programada correspondiente.
     * @throws ResourceNotFoundException Si la actividad no existe.
     */
    private ScheduledActivity getScheduledActivityById(Long id){
        return scheduledActivityRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("No se encontró este registro"));

    }


    /**
     * Recupera un empleado por su ID.
     *
     * @param id ID del empleado.
     * @return El empleado correspondiente.
     * @throws ResourceNotFoundException Si el empleado no existe.
     */
    private Employee getEmployeeById(Long id){
        return employeeRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(EMPLOYEE_NOT_FOUND));

    }


}
