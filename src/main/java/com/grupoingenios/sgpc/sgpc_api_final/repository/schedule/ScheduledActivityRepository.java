package com.grupoingenios.sgpc.sgpc_api_final.repository.schedule;

import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ScheduledActivityResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.schedule.ScheduledActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScheduledActivityRepository extends JpaRepository<ScheduledActivity, Long> {

    // Verificar si un schedule ya esta asignado a un ScheduledActivity
    boolean existsBySchedule_IdSchedule(Long idSchedule);

    //  Verificar si una actividad ya esta asignado a un ScheduledActivity
    boolean existsByActivity_IdActivity(Long idActivity);

    // Verificar la ducplcidad de actividad en un cronograma
    boolean existsBySchedule_IdScheduleAndActivity_IdActivity(Long scheduleId, Long activityId);

    // obtener el detalle de la actividad
    @Query("""
            SELECT NEW com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ScheduledActivityResponseDTO(\s
            sa.scheduledActivityId,
            sa.estimatedStartDate, sa.estimatedEndDate, sa.actualStartDate, sa.actualEndDate,\s
            sa.priority, sa.status, a.name) \s
            FROM ScheduledActivity sa\s
            JOIN sa.activity a \s
            WHERE sa.schedule.idSchedule = :scheduleId
           \s""")
    List<ScheduledActivityResponseDTO> findByScheduleId(@Param("scheduleId") Long scheduleId);



    /*
    // Verificar si tiene trabajadores asignados
    @Query("SELECT COUNT(saw) > 0 FROM ScheduledActivityWorker saw WHERE saw.scheduledActivity.id = :scheduledActivityId")
    boolean hasWorkerAssignments(@Param("scheduledActivityId") Long scheduledActivityId);

     */


    /*
     Consultar todas las actividades por cronograma, solo retorna los detalles que hay en SchceduleActivity con respecto
     a un schedule, es diferente al findAll().
     */
    List<ScheduledActivity> findAllBySchedule_IdSchedule(Long idSchedule);

}
