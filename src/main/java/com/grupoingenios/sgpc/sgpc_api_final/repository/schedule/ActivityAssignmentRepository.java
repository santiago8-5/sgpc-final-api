package com.grupoingenios.sgpc.sgpc_api_final.repository.schedule;

import com.grupoingenios.sgpc.sgpc_api_final.entity.schedule.ActivityAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ActivityAssignmentRepository extends JpaRepository<ActivityAssignment, Long> {

    // Validar si un empleado ya esta en una actividad programada
    boolean existsByScheduledActivity_ScheduledActivityIdAndEmployee_IdEmployee(Long scheduledActivityId, Long employeeId);

    // Verifica si hay al menos un responsable por actividad
    boolean existsByScheduledActivity_ScheduledActivityIdAndResponsibleTrue(Long scheduledActivityId);

    // Método derivado para validar si una asignación pertenece a una actividad programada
    boolean existsByScheduledActivity_ScheduledActivityIdAndActivityAssignmentId(Long scheduledActivityId, Long activityAssignmentId);

    //validación y la búsqueda de la asignación
    Optional<ActivityAssignment> findByScheduledActivity_ScheduledActivityIdAndActivityAssignmentId(Long scheduledActivityId, Long activityAssignmentId);

    // Recuperar asignaciones de una actividad programada
    List<ActivityAssignment> findByScheduledActivity_ScheduledActivityId(Long scheduledActivityId);


}
