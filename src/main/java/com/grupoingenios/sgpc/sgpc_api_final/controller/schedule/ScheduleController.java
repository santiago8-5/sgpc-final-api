package com.grupoingenios.sgpc.sgpc_api_final.controller.schedule;

import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ScheduleRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ScheduleResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ScheduledActivityRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ScheduledActivityResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.service.schedule.ScheduleService;
import com.grupoingenios.sgpc.sgpc_api_final.service.schedule.ScheduledActivityService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar operaciones relacionadas con cronogramas y actividades programadas.
 * Proporciona endpoints para realizar operaciones CRUD sobre cronogramas y sus actividades asociadas.
 */
@RestController
@RequestMapping("/api/v1/schedules")
@Validated
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final ScheduledActivityService scheduledActivityService;

    /**
     * Constructor para inyectar los servicios de cronogramas y actividades programadas.
     *
     * @param scheduleService Servicio que contiene la lógica de negocio para los cronogramas.
     * @param scheduledActivityService Servicio que contiene la lógica de negocio para las actividades programadas.
     */
    public ScheduleController(ScheduleService scheduleService, ScheduledActivityService scheduledActivityService) {
        this.scheduleService = scheduleService;
        this.scheduledActivityService = scheduledActivityService;
    }

    /**
     * Obtiene la lista de todos los cronogramas.
     *
     * @return Respuesta con la lista de cronogramas y un estado HTTP 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDTO>> getAllSchedules(){
        List<ScheduleResponseDTO> schedules = scheduleService.getAllSchedules();
        return ResponseEntity.ok(schedules);
    }

    /**
     * Obtiene los detalles de un cronograma por su ID.
     *
     * @param id ID del cronograma.
     * @return Respuesta con los detalles del cronograma y un estado HTTP 200 (OK).
     */
    @GetMapping("{id}")
    public ResponseEntity<ScheduleResponseDTO> getScheduleById(@PathVariable Long id){
        ScheduleResponseDTO schedule = scheduleService.getScheduleById(id);
        return ResponseEntity.ok(schedule);
    }

    /**
     * Actualiza un cronograma existente por su ID.
     *
     * @param id ID del cronograma a actualizar.
     * @param scheduleRequestDTO Datos actualizados del cronograma.
     * @return Respuesta con los datos del cronograma actualizado y un estado HTTP 200 (OK).
     */
    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDTO> updateSchedule(@PathVariable Long id, @Valid @RequestBody ScheduleRequestDTO scheduleRequestDTO ){
        ScheduleResponseDTO schedule = scheduleService.updateSchedule(id, scheduleRequestDTO);
        return ResponseEntity.ok(schedule);
    }

    /**
     * Elimina un cronograma por su ID.
     *
     * @param id ID del cronograma a eliminar.
     * @return Respuesta con un estado HTTP 204 (NO CONTENT) si la eliminación fue exitosa.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id){
        scheduleService.deleteSchedule(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    // ** Endpoints para actividades programadas **


    /**
     * Obtiene todas las actividades programadas asociadas a un cronograma.
     *
     * @param scheduleId ID del cronograma.
     * @return Respuesta con la lista de actividades programadas y un estado HTTP 200 (OK).
     */
    @GetMapping("/{scheduleId}/scheduled-activities")
    public ResponseEntity<List<ScheduledActivityResponseDTO>> getScheduledActivities(@PathVariable Long scheduleId) {
        List<ScheduledActivityResponseDTO> activities = scheduledActivityService.getScheduledActivitiesBySchedule(scheduleId);
        return ResponseEntity.ok(activities);
    }

    /**
     * Obtiene los detalles de una actividad programada específica por su ID.
     *
     * @param scheduleId ID del cronograma.
     * @param activityId ID de la actividad programada.
     * @return Respuesta con los detalles de la actividad programada y un estado HTTP 200 (OK).
     */
    @GetMapping("/{scheduleId}/activities/{activityId}")
    public ResponseEntity<ScheduledActivityResponseDTO> getScheduledActivityById(
            @PathVariable Long scheduleId,
            @PathVariable Long activityId) {
        ScheduledActivityResponseDTO activity = scheduleService.getScheduledActivityById(scheduleId, activityId);
        return ResponseEntity.ok(activity);
    }

    /**
     * Crea y asigna una nueva actividad programada a un cronograma.
     *
     * @param scheduleId ID del cronograma.
     * @param scheduledActivityRequestDTO Datos de la actividad programada a crear.
     * @return Respuesta con los datos de la actividad programada creada y un estado HTTP 201 (CREATED).
     */
    @PostMapping("/{scheduleId}/scheduled-activities")
    public ResponseEntity<ScheduledActivityResponseDTO> createScheduledActivity(@PathVariable Long scheduleId, @Valid @RequestBody ScheduledActivityRequestDTO scheduledActivityRequestDTO){
        ScheduledActivityResponseDTO scheduledActivity = scheduleService.createAndAssignScheduledActivity(scheduleId, scheduledActivityRequestDTO);
        return new ResponseEntity<>(scheduledActivity, HttpStatus.CREATED);
    }

    /**
     * Actualiza una actividad programada existente por su ID.
     *
     * @param scheduleId ID del cronograma.
     * @param scheduledActivityId ID de la actividad programada a actualizar.
     * @param scheduledActivityRequestDTO Datos actualizados de la actividad programada.
     * @return Respuesta con los datos de la actividad programada actualizada y un estado HTTP 200 (OK).
     */
    @PutMapping("/{scheduleId}/scheduled-activities/{scheduledActivityId}")
    public ResponseEntity<ScheduledActivityResponseDTO> updateScheduledActivity(@PathVariable Long scheduleId, @PathVariable Long scheduledActivityId,
                                                                                @Valid @RequestBody ScheduledActivityRequestDTO scheduledActivityRequestDTO) {
        ScheduledActivityResponseDTO scheduledActivity = scheduleService.updateScheduleActivity(scheduleId, scheduledActivityId, scheduledActivityRequestDTO);
        return ResponseEntity.ok(scheduledActivity);
    }

    /**
     * Elimina una actividad programada de un cronograma.
     *
     * @param scheduleId ID del cronograma.
     * @param scheduledActivityId ID de la actividad programada a eliminar.
     * @return Respuesta con un estado HTTP 204 (NO CONTENT) si la eliminación fue exitosa.
     */
    @DeleteMapping("/{scheduleId}/scheduled-activities/{scheduledActivityId}")
    public ResponseEntity<Void> deleteScheduledActivity(@PathVariable Long scheduleId, @PathVariable Long scheduledActivityId){
        scheduleService.deleteScheduledActivity(scheduleId, scheduledActivityId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}