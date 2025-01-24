package com.grupoingenios.sgpc.sgpc_api_final.controller.schedule;

import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ActivityAssignmentResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.service.schedule.ActivityAssignmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controlador REST para gestionar la asignación de actividades.
 * Proporciona un endpoint para obtener todas las asignaciones de actividades.
 */
@RestController
@RequestMapping("/api/v1/activity-assignment")
@Validated
public class ActivityAssignmentController {

    private final ActivityAssignmentService activityAssignmentService;

    /**
     * Constructor para inyectar el servicio de asignación de actividades.
     *
     * @param activityAssignmentService Servicio que contiene la lógica de negocio para asignaciones de actividades.
     */
    public ActivityAssignmentController(ActivityAssignmentService activityAssignmentService) {
        this.activityAssignmentService = activityAssignmentService;
    }

    /**
     * Obtiene la lista de todas las asignaciones de actividades.
     *
     * @return Respuesta con la lista de asignaciones de actividades y un estado HTTP 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<ActivityAssignmentResponseDTO>> getAllActivityAssignment(){
        List<ActivityAssignmentResponseDTO> activityAssignments = activityAssignmentService
                .getAllActivityAssignment();
        return ResponseEntity.ok(activityAssignments);
    }

}
