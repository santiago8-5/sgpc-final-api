package com.grupoingenios.sgpc.sgpc_api_final.repository.schedule;

import com.grupoingenios.sgpc.sgpc_api_final.entity.schedule.ActivityAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad `ActivityAssignment`.
 * Proporciona métodos para realizar operaciones CRUD sobre las asignaciones de actividades,
 * así como consultas personalizadas para verificar la existencia de asignaciones específicas y recuperarlas.
 */
public interface ActivityAssignmentRepository extends JpaRepository<ActivityAssignment, Long> {


    /**
     * Verifica si un empleado ya está asignado a una actividad programada.
     *
     * @param scheduledActivityId El ID de la actividad programada.
     * @param employeeId El ID del empleado.
     * @return `true` si el empleado ya está asignado a la actividad, de lo contrario `false`.
     */
    boolean existsByScheduledActivity_ScheduledActivityIdAndEmployee_IdEmployee(Long scheduledActivityId, Long employeeId);

    /**
     * Verifica si existe al menos un responsable para una actividad programada.
     *
     * @param scheduledActivityId El ID de la actividad programada.
     * @return `true` si existe al menos un responsable asignado a la actividad, de lo contrario `false`.
     */
    boolean existsByScheduledActivity_ScheduledActivityIdAndResponsibleTrue(Long scheduledActivityId);

    /**
     * Verifica si una asignación pertenece a una actividad programada específica.
     *
     * @param scheduledActivityId El ID de la actividad programada.
     * @param activityAssignmentId El ID de la asignación de actividad.
     * @return `true` si la asignación pertenece a la actividad programada, de lo contrario `false`.
     */
    boolean existsByScheduledActivity_ScheduledActivityIdAndActivityAssignmentId(Long scheduledActivityId, Long activityAssignmentId);

    /**
     * Busca una asignación específica para una actividad programada.
     *
     * @param scheduledActivityId El ID de la actividad programada.
     * @param activityAssignmentId El ID de la asignación de actividad.
     * @return Un `Optional` que contiene la asignación si existe, de lo contrario está vacío.
     */
    Optional<ActivityAssignment> findByScheduledActivity_ScheduledActivityIdAndActivityAssignmentId(Long scheduledActivityId, Long activityAssignmentId);


    /**
     * Recupera todas las asignaciones de una actividad programada.
     *
     * @param scheduledActivityId El ID de la actividad programada.
     * @return Una lista de asignaciones de actividad para la actividad programada.
     */
    List<ActivityAssignment> findByScheduledActivity_ScheduledActivityId(Long scheduledActivityId);


}
