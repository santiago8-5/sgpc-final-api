package com.grupoingenios.sgpc.sgpc_api_final.controller.schedule;


import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ActivityAssignmentRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ActivityAssignmentResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ScheduledActivityResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.service.schedule.ScheduledActivityService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Controlador REST para gestionar actividades programadas y sus asignaciones.
 * Proporciona endpoints para CRUD de actividades programadas y asignaciones relacionadas.
 */
@RestController
@RequestMapping("/api/v1/scheduled-activities")
@Validated
public class ScheduledActivityController {

    private final ScheduledActivityService scheduledActivityService;

    /**
     * Constructor para inyectar el servicio de actividades programadas.
     *
     * @param scheduledActivityService Servicio que contiene la lógica de negocio para actividades programadas.
     */
    public ScheduledActivityController(ScheduledActivityService scheduledActivityService) {
        this.scheduledActivityService = scheduledActivityService;
    }

    /**
     * Obtiene la lista de todas las actividades programadas.
     *
     * @return Respuesta con la lista de actividades programadas y un estado HTTP 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<ScheduledActivityResponseDTO>> getAllScheduleActivity(){
        List<ScheduledActivityResponseDTO>  scheduledActivities = scheduledActivityService.getAllScheduleActivity();
        return ResponseEntity.ok(scheduledActivities);
    }

    /**
     * Crea una nueva asignación de empleado para una actividad programada.
     *
     * @param scheduledActivityId ID de la actividad programada.
     * @param activityAssignmentRequestDTO Datos de la asignación.
     * @return Respuesta con los datos de la asignación creada y un estado HTTP 201 (CREATED).
     */
    @PostMapping("/{scheduledActivityId}/assignment-employee")
    public ResponseEntity<ActivityAssignmentResponseDTO> createActivityAssignment(@PathVariable Long scheduledActivityId,  @Valid @RequestBody ActivityAssignmentRequestDTO activityAssignmentRequestDTO){
        ActivityAssignmentResponseDTO activityAssignment = scheduledActivityService
                .createActivityAssignment(scheduledActivityId, activityAssignmentRequestDTO);
        return new ResponseEntity<>(activityAssignment, HttpStatus.CREATED);
    }

    /**
     * Actualiza una asignación de empleado existente para una actividad programada.
     *
     * @param scheduledActivityId ID de la actividad programada.
     * @param activityAssignmentId ID de la asignación a actualizar.
     * @param activityAssignmentRequestDTO Datos actualizados de la asignación.
     * @return Respuesta con los datos de la asignación actualizada y un estado HTTP 200 (OK).
     */
    @PutMapping("/{scheduledActivityId}/assignment-employee/{activityAssignmentId}")
    public ResponseEntity<ActivityAssignmentResponseDTO> updateActivityAssignment(@PathVariable Long scheduledActivityId, @PathVariable Long activityAssignmentId, @Valid @RequestBody ActivityAssignmentRequestDTO activityAssignmentRequestDTO){
        ActivityAssignmentResponseDTO activityAssignment = scheduledActivityService
                .updateActivityAssignment(scheduledActivityId, activityAssignmentId, activityAssignmentRequestDTO);
        return ResponseEntity.ok(activityAssignment);
    }

    /**
     * Elimina una asignación de empleado de una actividad programada.
     *
     * @param scheduledActivityId ID de la actividad programada.
     * @param activityAssignmentId ID de la asignación a eliminar.
     * @return Respuesta con un estado HTTP 204 (NO CONTENT) si la eliminación fue exitosa.
     */
    @DeleteMapping("/{scheduledActivityId}/assignment-employee/{activityAssignmentId}")
    public ResponseEntity<Void> deleteActivityAssignment(@PathVariable Long scheduledActivityId, @PathVariable Long activityAssignmentId){
        scheduledActivityService.deleteActivityAssignment(scheduledActivityId, activityAssignmentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Verifica si una actividad programada tiene un responsable asignado.
     *
     * @param scheduledActivityId ID de la actividad programada.
     * @return Respuesta con un mapa que indica si la actividad tiene un responsable y un estado HTTP 200 (OK).
     */
    @GetMapping("/{scheduledActivityId}/has-responsible")
    public ResponseEntity<Map<String, Boolean>> hasResponsible(@PathVariable Long scheduledActivityId) {
        boolean hasResponsible = scheduledActivityService.checkIfActivityHasResponsible(scheduledActivityId);
        return ResponseEntity.ok(Collections.singletonMap("hasResponsible", hasResponsible));
    }

    /**
     * Obtiene las asignaciones asociadas a una actividad programada.
     *
     * @param scheduledActivityId ID de la actividad programada.
     * @return Respuesta con la lista de asignaciones y un estado HTTP 200 (OK).
     */
    @GetMapping("/{scheduledActivityId}/assignments")
    public ResponseEntity<List<ActivityAssignmentResponseDTO>> getAssignmentsByScheduledActivity(
            @PathVariable Long scheduledActivityId) {
        List<ActivityAssignmentResponseDTO> assignments = scheduledActivityService
                .getAssignmentsByScheduledActivityId(scheduledActivityId);
        return ResponseEntity.ok(assignments);
    }

}
