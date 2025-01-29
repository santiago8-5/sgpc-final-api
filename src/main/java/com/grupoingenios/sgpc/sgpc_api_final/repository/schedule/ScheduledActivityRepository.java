package com.grupoingenios.sgpc.sgpc_api_final.repository.schedule;

import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ScheduledActivityResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.schedule.ScheduledActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Repositorio para la entidad `ScheduledActivity`.
 * Proporciona métodos para realizar operaciones sobre las actividades programadas dentro de un cronograma,
 * así como validaciones y consultas personalizadas para la gestión de actividades en un cronograma.
 */
public interface ScheduledActivityRepository extends JpaRepository<ScheduledActivity, Long> {

    /**
     * Verifica si un cronograma ya está asignado a una actividad programada.
     *
     * @param idSchedule El ID del cronograma.
     * @return `true` si el cronograma está asignado a una actividad programada, de lo contrario `false`.
     */
    boolean existsBySchedule_IdSchedule(Long idSchedule);

    /**
     * Verifica si una actividad ya está asignada a una actividad programada.
     *
     * @param idActivity El ID de la actividad.
     * @return `true` si la actividad está asignada a una actividad programada, de lo contrario `false`.
     */
    boolean existsByActivity_IdActivity(Long idActivity);

    /**
     * Verifica la duplicidad de una actividad en un cronograma.
     *
     * @param scheduleId El ID del cronograma.
     * @param activityId El ID de la actividad.
     * @return `true` si la actividad está duplicada en el cronograma, de lo contrario `false`.
     */
    boolean existsBySchedule_IdScheduleAndActivity_IdActivity(Long scheduleId, Long activityId);

    /**
     * Verifica la duplicidad de una actividad en un cronograma al crear o actualizar,
     * excluyendo una actividad programada específica.
     *
     * @param scheduleId El ID del cronograma.
     * @param activityId El ID de la actividad.
     * @param excludedScheduledActivityId El ID de la actividad programada que debe ser excluida de la verificación.
     * @return `true` si la actividad está duplicada en el cronograma, excluyendo la actividad programada especificada, de lo contrario `false`.
     */
    boolean existsBySchedule_IdScheduleAndActivity_IdActivityAndScheduledActivityIdNot(Long scheduleId, Long activityId, Long excludedScheduledActivityId);


    /**
     * Obtiene los detalles de las actividades programadas de un cronograma.
     *
     * @param scheduleId El ID del cronograma.
     * @return Una lista de `ScheduledActivityResponseDTO` que contiene los detalles de las actividades programadas del cronograma.
     */
    @Query("""
            SELECT NEW com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ScheduledActivityResponseDTO(
            sa.scheduledActivityId,
            sa.estimatedStartDate,
            sa.estimatedEndDate,
            sa.actualStartDate,
            sa.actualEndDate,
            sa.priority,\s
            sa.status,\s
            a.name,
            a.idActivity)
            FROM ScheduledActivity sa
            JOIN sa.activity a\s
            WHERE sa.schedule.idSchedule = :scheduleId
          \s""")
    List<ScheduledActivityResponseDTO> findByScheduleId(@Param("scheduleId") Long scheduleId);



    /*
    // Verificar si tiene trabajadores asignados
    @Query("SELECT COUNT(saw) > 0 FROM ScheduledActivityWorker saw WHERE saw.scheduledActivity.id = :scheduledActivityId")
    boolean hasWorkerAssignments(@Param("scheduledActivityId") Long scheduledActivityId);

     */


    /**
     * Recupera todas las actividades programadas de un cronograma.
     *
     * @param idSchedule El ID del cronograma.
     * @return Una lista de actividades programadas asociadas al cronograma.
     */
    List<ScheduledActivity> findAllBySchedule_IdSchedule(Long idSchedule);

}
