package com.grupoingenios.sgpc.sgpc_api_final.controller.work;

import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ScheduleRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ScheduleResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ScheduledActivityRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ScheduledActivityResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.work.ClientWorkRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.work.ClientWorkResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.work.WorkRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.work.WorkResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.service.work.WorkService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controlador REST para gestionar operaciones relacionadas con obras.
 * Proporciona endpoints para realizar operaciones CRUD sobre obras, cronogramas, actividades programadas y relaciones con clientes y proveedores.
 */
@RestController
@RequestMapping("/api/v1/works")
@Validated
public class WorkController {

    private final WorkService workService;

    /**
     * Constructor para inyectar el servicio de obras.
     *
     * @param workService Servicio que contiene la lógica de negocio para las obras.
     */
    public WorkController(WorkService workService) {
        this.workService = workService;
    }

    // ** Endpoints para obras **

    /**
     * Obtiene la lista de todas las obras.
     *
     * @return Lista de obras y un estado HTTP 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<WorkResponseDTO>> getAllWorks(){
        List<WorkResponseDTO> works = workService.getAllWorks();
        return ResponseEntity.ok(works);
    }

    /**
     * Obtiene los detalles de una obra por su ID.
     *
     * @param id ID de la obra.
     * @return Detalles de la obra y un estado HTTP 200 (OK).
     */
    @GetMapping("/{id}")
    public ResponseEntity<WorkResponseDTO> getWorkById(@PathVariable long id){
        WorkResponseDTO work = workService.getWorkById(id);
        return ResponseEntity.ok(work);
    }

    /**
     * Crea una nueva obra.
     *
     * @param workRequestDTO Datos de la obra a crear.
     * @return Obra creada y un estado HTTP 201 (CREATED).
     */
    @PostMapping
    public ResponseEntity<WorkResponseDTO> createWork(@Valid @RequestBody WorkRequestDTO workRequestDTO){
        WorkResponseDTO work = workService.createWork(workRequestDTO);
        return new ResponseEntity<>(work, HttpStatus.CREATED);
    }

    /**
     * Actualiza una obra existente por su ID.
     *
     * @param id ID de la obra.
     * @param workRequestDTO Datos actualizados de la obra.
     * @return Obra actualizada y un estado HTTP 200 (OK).
     */
    @PutMapping("/{id}")
    public ResponseEntity<WorkResponseDTO> updateWork(@Valid @PathVariable Long id, @RequestBody WorkRequestDTO workRequestDTO){
        WorkResponseDTO work = workService.updateWork(id, workRequestDTO);
        return ResponseEntity.ok(work);
    }


    /**
     * Elimina una obra por su ID.
     *
     * @param id ID de la obra.
     * @return Estado HTTP 204 (NO CONTENT).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWork(@PathVariable Long id){
        workService.deleteWork(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    // ** Endpoints de Relaciones Cliente-Obra **

    /**
     * Obtiene las relaciones cliente-obra asociadas a una obra.
     *
     * @param idWork ID de la obra.
     * @return Lista de relaciones cliente-obra y un estado HTTP 200 (OK).
     */
    @GetMapping("/{idWork}/client-works")
    public ResponseEntity<List<ClientWorkResponseDTO>> getClientWorksByWorkId(@PathVariable Long idWork) {
        List<ClientWorkResponseDTO> clientWorks = workService.getClientWorksByWorkId(idWork);
        return ResponseEntity.ok(clientWorks);
    }


    /**
     * Crea una relación cliente-obra.
     *
     * @param idWork             ID de la obra.
     * @param clientWorkRequestDTO Datos de la relación cliente-obra.
     * @return Relación cliente-obra creada y un estado HTTP 201 (CREATED).
     */
    @PostMapping("/{idWork}/work-client")
    public ResponseEntity<ClientWorkResponseDTO> assignClientToWork(@PathVariable Long idWork, @RequestBody @Valid ClientWorkRequestDTO clientWorkRequestDTO){
        ClientWorkResponseDTO clientToWork = workService.assignClientToWork(idWork, clientWorkRequestDTO);
        return new ResponseEntity<>(clientToWork, HttpStatus.CREATED);
    }


    /**
     * Actualiza una relación cliente-obra.
     *
     * @param idWork             ID de la obra.
     * @param clientWorkId       ID de la relación cliente-obra.
     * @param clientWorkRequestDTO Datos actualizados de la relación cliente-obra.
     * @return Relación cliente-obra actualizada y un estado HTTP 200 (OK).
     */
    @PutMapping("/{idWork}/work-client/{clientWorkId}")
    public ResponseEntity<ClientWorkResponseDTO> updateClientWork(@PathVariable Long idWork, @PathVariable Long clientWorkId, @RequestBody @Valid ClientWorkRequestDTO clientWorkRequestDTO){
        ClientWorkResponseDTO clientToWork = workService.updateClientWork(idWork, clientWorkId,  clientWorkRequestDTO);
        return ResponseEntity.ok(clientToWork);

    }


    /**
     * Elimina una relación cliente-obra.
     *
     * @param idWork       ID de la obra.
     * @param clientWorkId ID de la relación cliente-obra.
     * @return Estado HTTP 204 (NO CONTENT).
     */
    @DeleteMapping("/{idWork}/work-client/{clientWorkId}")
    public ResponseEntity<ClientWorkResponseDTO> deleteClientWork(@PathVariable Long idWork, @PathVariable Long clientWorkId){
        workService.deleteClientWork(clientWorkId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    // ** Endpoints de Cronogramas y Actividades Programadas **


    /**
     * Crea y asigna un cronograma a una obra.
     *
     * @param idWork            ID de la obra.
     * @param scheduleRequestDTO Datos del cronograma a crear.
     * @return Cronograma creado y un estado HTTP 201 (CREATED).
     */
    @PostMapping("/{idWork}/schedule")
    public ResponseEntity<ScheduleResponseDTO> createAndAssignSchedule(@PathVariable Long idWork, @Valid @RequestBody ScheduleRequestDTO scheduleRequestDTO){
        ScheduleResponseDTO schedule = workService.createAndAssignSchedule(idWork, scheduleRequestDTO);
        return new ResponseEntity<>(schedule, HttpStatus.CREATED);
    }


    /**
     * Obtiene el cronograma asociado a una obra.
     *
     * @param idWork ID de la obra.
     * @return Detalles del cronograma y un estado HTTP 200 (OK).
     */
    @GetMapping("/{idWork}/schedule")
    public ResponseEntity<ScheduleResponseDTO> getScheduleByWork(@PathVariable Long idWork) {
        ScheduleResponseDTO schedule = workService.getScheduleByWorkId(idWork);
        return ResponseEntity.ok(schedule);
    }


    /**
     * Obtiene todas las actividades programadas asociadas al cronograma de una obra.
     *
     * @param idWork ID de la obra.
     * @return Lista de actividades programadas y un estado HTTP 200 (OK).
     */
    @GetMapping("/{idWork}/schedule/activities")
    public ResponseEntity<List<ScheduledActivityResponseDTO>> getActivitiesBySchedule(@PathVariable Long idWork) {
        List<ScheduledActivityResponseDTO> activities = workService.getScheduledActivitiesByWorkId(idWork);
        return ResponseEntity.ok(activities);
    }


    /**
     * Crea una actividad programada en el contexto de una obra.
     *
     * @param idWork                     ID de la obra.
     * @param scheduledActivityRequestDTO Datos de la actividad programada.
     * @return Actividad programada creada y un estado HTTP 201 (CREATED).
     */
    @PostMapping("/{idWork}/schedule/activities")
    public ResponseEntity<ScheduledActivityResponseDTO> createScheduledActivityFromWork(
            @PathVariable Long idWork,
            @Valid @RequestBody ScheduledActivityRequestDTO scheduledActivityRequestDTO) {

        ScheduledActivityResponseDTO scheduledActivity = workService.createScheduledActivityFromWork(idWork, scheduledActivityRequestDTO);
        return new ResponseEntity<>(scheduledActivity, HttpStatus.CREATED);
    }


    /**
     * Actualiza una actividad programada asociada a una obra.
     *
     * @param idWork                     ID de la obra.
     * @param idScheduledActivity         ID de la actividad programada.
     * @param scheduledActivityRequestDTO Datos actualizados de la actividad programada.
     * @return Actividad programada actualizada y un estado HTTP 200 (OK).
     */
    @PutMapping("/{idWork}/schedule/activities/{idScheduledActivity}")
    public ResponseEntity<ScheduledActivityResponseDTO> updateScheduledActivityFromWork(
            @PathVariable Long idWork,
            @PathVariable Long idScheduledActivity,
            @Valid @RequestBody ScheduledActivityRequestDTO scheduledActivityRequestDTO) {

        ScheduledActivityResponseDTO updatedScheduledActivity = workService.updateScheduledActivityFromWork(
                idWork, idScheduledActivity, scheduledActivityRequestDTO);

        return ResponseEntity.ok(updatedScheduledActivity);
    }


    /**
     * Elimina una actividad programada asociada a una obra.
     *
     * @param idWork             ID de la obra.
     * @param idScheduledActivity ID de la actividad programada.
     * @return Estado HTTP 204 (NO CONTENT).
     */
    @DeleteMapping("/{idWork}/schedule/activities/{idScheduledActivity}")
    public ResponseEntity<Void> deleteScheduledActivityFromWork(
            @PathVariable Long idWork,
            @PathVariable Long idScheduledActivity) {
        workService.deleteScheduledActivityFromWork(idWork, idScheduledActivity);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    /**
     * Obtiene una actividad programada específica asociada al cronograma de una obra.
     *
     * @param idWork             ID de la obra.
     * @param idScheduledActivity ID de la actividad programada.
     * @return Detalles de la actividad programada y un estado HTTP 200 (OK).
     */
    @GetMapping("/{idWork}/schedule/activities/{idScheduledActivity}")
    public ResponseEntity<ScheduledActivityResponseDTO> getScheduledActivityFromWork(
            @PathVariable Long idWork,
            @PathVariable Long idScheduledActivity) {

        ScheduledActivityResponseDTO scheduledActivity = workService.getScheduledActivityFromWork(idWork, idScheduledActivity);
        return ResponseEntity.ok(scheduledActivity);
    }


    // ** Endpoints de Relaciones Proveedor-Obra **

    /**
     * Asigna un proveedor a una obra.
     *
     * @param workId     ID de la obra.
     * @param supplierId ID del proveedor.
     * @return Estado HTTP 201 (CREATED).
     */
    @PostMapping("/{workId}/suppliers/{supplierId}")
    public ResponseEntity<Void> assignSupplierToWork(
            @PathVariable Long workId,
            @PathVariable Long supplierId) {
        workService.assignSupplierToWork(workId, supplierId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
