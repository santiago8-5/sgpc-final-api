package com.grupoingenios.sgpc.sgpc_api_final.controller.schedule;

import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ActivityRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ActivityResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.service.schedule.ActivityService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controlador REST para gestionar operaciones relacionadas con actividades.
 * Proporciona endpoints para realizar operaciones CRUD sobre las actividades.
 */
@RestController
@RequestMapping("/api/v1/activities")
@Validated
public class ActivityController {

    private final ActivityService activityService;

    /**
     * Constructor para inyectar el servicio de actividades.
     *
     * @param activityService Servicio que contiene la lógica de negocio para las actividades.
     */
    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    /**
     * Obtiene la lista de todas las actividades.
     *
     * @return Respuesta con la lista de actividades y un estado HTTP 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<ActivityResponseDTO>> getAllActivity(){
        List<ActivityResponseDTO> activities = activityService.getAllActivity();
        return ResponseEntity.ok(activities);
    }

    /**
     * Crea una nueva actividad.
     *
     * @param activityRequestDTO Datos de la actividad a crear.
     * @return Respuesta con los datos de la actividad creada y un estado HTTP 201 (CREATED).
     */
    @PostMapping
    public ResponseEntity<ActivityResponseDTO> createActivity(@RequestBody @Valid ActivityRequestDTO activityRequestDTO){
        ActivityResponseDTO activity = activityService.createActivity(activityRequestDTO);
        return new ResponseEntity<>(activity, HttpStatus.CREATED);
    }

    /**
     * Actualiza una actividad existente por su ID.
     *
     * @param id                  ID de la actividad a actualizar.
     * @param activityRequestDTO Datos actualizados de la actividad.
     * @return Respuesta con los datos de la actividad actualizada y un estado HTTP 200 (OK).
     */
    @PutMapping("/{id}")
    public ResponseEntity<ActivityResponseDTO> updateActivity(@PathVariable Long id, @Valid @RequestBody ActivityRequestDTO activityRequestDTO){
        ActivityResponseDTO activity = activityService.updateActivity(id, activityRequestDTO);
        return ResponseEntity.ok(activity);
    }

    /**
     * Elimina una actividad por su ID.
     *
     * @param id ID de la actividad a eliminar.
     * @return Respuesta con un estado HTTP 204 (NO CONTENT) si la eliminación fue exitosa.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable Long id){
        activityService.deleteActivity(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
