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
import java.util.stream.Collectors;

import static com.grupoingenios.sgpc.sgpc_api_final.constants.AppConstant.EMPLOYEE_NOT_FOUND;

@Service
public class ScheduledActivityService {

    private final ScheduledActivityRepository scheduledActivityRepository;
    private final ScheduledActivityMapper scheduledActivityMapper;
    private final ActivityAssignmentRepository activityAssignmentRepository;
    private final ActivityAssignmentMapper activityAssignmentMapper;
    private final EmployeeRepository employeeRepository;


    public ScheduledActivityService(ScheduledActivityRepository scheduledActivityRepository, ScheduledActivityMapper scheduledActivityMapper,
                                    ActivityAssignmentRepository activityAssignmentRepository, ActivityAssignmentMapper activityAssignmentMapper, EmployeeRepository employeeRepository) {
        this.scheduledActivityRepository = scheduledActivityRepository;
        this.scheduledActivityMapper = scheduledActivityMapper;
        this.activityAssignmentRepository = activityAssignmentRepository;
        this.activityAssignmentMapper = activityAssignmentMapper;
        this.employeeRepository = employeeRepository;
    }

    @Transactional(readOnly = true)
    public List<ScheduledActivityResponseDTO> getAllScheduleActivity(){
        return scheduledActivityRepository
                .findAll()
                .stream()
                .map(scheduledActivityMapper::toResponseDto)
                .toList();
    }

    // Método para verificar si existe un responsable
    public boolean checkIfActivityHasResponsible(Long scheduledActivityId) {
        return activityAssignmentRepository.existsByScheduledActivity_ScheduledActivityIdAndResponsibleTrue(scheduledActivityId);
    }

    // Obtener todas las asignaciones de una actividad programada
    @Transactional(readOnly = true)
    public List<ActivityAssignmentResponseDTO> getAssignmentsByScheduledActivityId(Long scheduledActivityId) {
        return activityAssignmentRepository
                .findByScheduledActivity_ScheduledActivityId(scheduledActivityId)
                .stream()
                .map(activityAssignmentMapper::toResponseDto)
                .toList();
    }

    // retornar detalles de scheduled activity
    @Transactional(readOnly = true)
    public List<ScheduledActivityResponseDTO> getScheduledActivitiesBySchedule(Long scheduleId){
        return scheduledActivityRepository
                .findByScheduleId(scheduleId);
    }


    // crear una asignacion de empleado
    @Transactional
    public ActivityAssignmentResponseDTO createActivityAssignment(Long scheduledActivityId, ActivityAssignmentRequestDTO activityAssignmentRequestDTO){

        // Validar duplicidad de asignación
        validateUniqueAssignment(scheduledActivityId, activityAssignmentRequestDTO.getEmployeeId());

        // Recuperar ScheduledActivity PÓR ID
        ScheduledActivity scheduledActivity = getScheduledActivityById(scheduledActivityId);

        // Recuperar Employee
        Employee employee = getEmployeeById(activityAssignmentRequestDTO.getEmployeeId());


        // Transformar el request dto a entity
        ActivityAssignment activityAssignment = activityAssignmentMapper.toEntity(activityAssignmentRequestDTO);

        // Asignar el ScheduledActivity y Employee
        activityAssignment.setScheduledActivity(scheduledActivity);
        activityAssignment.setEmployee(employee);

        ActivityAssignment savedActivityAssignment = activityAssignmentRepository.save(activityAssignment);

        // Validar que haya al menos un responsable despues de crear
        validateAtLeastOneResponsible(scheduledActivity.getScheduledActivityId());

        return activityAssignmentMapper.toResponseDto(savedActivityAssignment);

    }


    // editar una asignacion a un empleado
    @Transactional
    public ActivityAssignmentResponseDTO updateActivityAssignment(Long scheduledActivityId, Long activityAssignmentId, ActivityAssignmentRequestDTO activityAssignmentRequestDTO) {

        // Recuperar asignación existente
        ActivityAssignment existingActivityAssignment = activityAssignmentRepository
                .findById(activityAssignmentId).orElseThrow(() -> new ResourceNotFoundException("Registro no encontrado"));

        // Validar que la asignación pertenece a la actividad programada
        if (!activityAssignmentRepository.existsByScheduledActivity_ScheduledActivityIdAndActivityAssignmentId(scheduledActivityId, activityAssignmentId)) {
            throw new ResourceNotFoundException("La asignación no pertenece a la actividad programada especificada o no existe.");
        }

        // Validar duplicidad de asignación si el EmployeeId ha cambiado
        if (!existingActivityAssignment.getEmployee().getIdEmployee().equals(activityAssignmentRequestDTO.getEmployeeId())) {
            validateUniqueAssignment(scheduledActivityId, activityAssignmentRequestDTO.getEmployeeId());
        }

        // Validar si el responsable actual deja de ser responsable
        if (existingActivityAssignment.isResponsible() && !activityAssignmentRequestDTO.isResponsible()) {
            validateAtLeastOneResponsible(existingActivityAssignment.getScheduledActivity().getScheduledActivityId());
        }

        // Si el EmployeeId ha cambiado, recuperar el nuevo empleado
        if (!existingActivityAssignment.getEmployee().getIdEmployee().equals(activityAssignmentRequestDTO.getEmployeeId())) {
            Employee newEmployee = getEmployeeById(activityAssignmentRequestDTO.getEmployeeId());
            existingActivityAssignment.setEmployee(newEmployee);
        }

        // Actualizar los demás campos desde el DTO
        activityAssignmentMapper.updateActivityAssignmentFromDTO(activityAssignmentRequestDTO, existingActivityAssignment);

        // Guardar registro
        ActivityAssignment updatedActivityAssignment = activityAssignmentRepository.save(existingActivityAssignment);

        return activityAssignmentMapper.toResponseDto(updatedActivityAssignment);
    }

    //Eliminar una asignación a un empleado
    @Transactional
    public void deleteActivityAssignment(Long scheduledActivityId, Long activityAssignmentId){

        // Validar que la asignación pertenece a la actividad programada
        if (!activityAssignmentRepository.existsByScheduledActivity_ScheduledActivityIdAndActivityAssignmentId(scheduledActivityId, activityAssignmentId)) {
            throw new ResourceNotFoundException("La asignación no pertenece a la actividad programada especificada o no existe.");
        }

        activityAssignmentRepository.deleteById(activityAssignmentId);

    }


    private void validateUniqueAssignment(Long scheduledActivityId, Long employeeId) {
        if (activityAssignmentRepository.existsByScheduledActivity_ScheduledActivityIdAndEmployee_IdEmployee(scheduledActivityId, employeeId)) {
            throw new BadRequestException("El empleado ya está asignado a esta actividad programada.");
        }
    }

    private void validateAtLeastOneResponsible(Long scheduledActivityId) {
        boolean hasResponsible = activityAssignmentRepository.existsByScheduledActivity_ScheduledActivityIdAndResponsibleTrue(scheduledActivityId);
        if (!hasResponsible) {
            throw new BadRequestException("Debe haber al menos un empleado responsable para esta actividad programada.");
        }
    }

    private ScheduledActivity getScheduledActivityById(Long id){
        return scheduledActivityRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("No se encontró este registro"));

    }

    private Employee getEmployeeById(Long id){
        return employeeRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(EMPLOYEE_NOT_FOUND));

    }


}
