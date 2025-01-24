package com.grupoingenios.sgpc.sgpc_api_final.controller.maintenance;

import com.grupoingenios.sgpc.sgpc_api_final.dto.maintenance.MaintenanceRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.maintenance.MaintenanceResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.service.maintenance.MaintenanceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controlador REST para gestionar operaciones relacionadas con mantenimientos.
 * Proporciona endpoints para realizar operaciones CRUD sobre mantenimientos.
 */
@RestController
@RequestMapping("/api/v1/maintenance")
@Validated
public class MaintenanceController {

    private final MaintenanceService maintenanceService;

    /**
     * Constructor para inyectar el servicio de mantenimiento.
     *
     * @param maintenanceService Servicio que contiene la lógica de negocio para los mantenimientos.
     */
    public MaintenanceController(MaintenanceService maintenanceService) {
        this.maintenanceService = maintenanceService;
    }

    /**
     * Obtiene la lista de todos los mantenimientos.
     *
     * @return Respuesta con la lista de mantenimientos y un estado HTTP 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<MaintenanceResponseDTO>> getAllMaintenance(){
        List<MaintenanceResponseDTO> maintenance = maintenanceService.getAllMaintenance();
        return ResponseEntity.ok(maintenance);
    }

    /**
     * Crea un nuevo mantenimiento.
     *
     * @param maintenanceRequestDTO Datos del mantenimiento a crear.
     * @return Respuesta con los datos del mantenimiento creado y un estado HTTP 201 (CREATED).
     */
    @PostMapping
    public ResponseEntity<MaintenanceResponseDTO> createMaintenance(@RequestBody @Valid MaintenanceRequestDTO maintenanceRequestDTO){
        MaintenanceResponseDTO maintenance = maintenanceService.createMaintenance(maintenanceRequestDTO);
        return new ResponseEntity<>(maintenance, HttpStatus.CREATED);
    }

    /**
     * Actualiza un mantenimiento existente por su ID.
     *
     * @param id                   ID del mantenimiento a actualizar.
     * @param maintenanceRequestDTO Datos actualizados del mantenimiento.
     * @return Respuesta con los datos del mantenimiento actualizado y un estado HTTP 200 (OK).
     */
    @PutMapping("/{id}")
    public ResponseEntity<MaintenanceResponseDTO> createMaintenance(@PathVariable Long id, @RequestBody @Valid MaintenanceRequestDTO maintenanceRequestDTO){
        MaintenanceResponseDTO maintenance = maintenanceService.updateMaintenance(id, maintenanceRequestDTO);
        return ResponseEntity.ok(maintenance);
    }

    /**
     * Elimina un mantenimiento por su ID.
     *
     * @param id ID del mantenimiento a eliminar.
     * @return Respuesta con un estado HTTP 204 (NO CONTENT) si la eliminación fue exitosa.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaintenance(@PathVariable Long id){
        maintenanceService.deleteMaintenance(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
