package com.grupoingenios.sgpc.sgpc_api_final.controller.vehicle;

import com.grupoingenios.sgpc.sgpc_api_final.dto.vehicle.VehicleRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.vehicle.VehicleResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.service.vehicle.VehicleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controlador REST para gestionar operaciones relacionadas con vehículos.
 * Proporciona endpoints para realizar operaciones CRUD sobre los vehículos.
 */
@RestController
@RequestMapping("/api/v1/vehicles")
@Validated
public class VehicleController {

    private final VehicleService vehicleService;

    /**
     * Constructor para inyectar el servicio de vehículos.
     *
     * @param vehicleService Servicio que contiene la lógica de negocio para los vehículos.
     */
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    /**
     * Obtiene la lista de todos los vehículos.
     *
     * @return Respuesta con la lista de vehículos y un estado HTTP 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<VehicleResponseDTO>> getAllVehicles(){
        List<VehicleResponseDTO> vehicles = vehicleService.getAllVehicles();
        return ResponseEntity.ok(vehicles);
    }

    /**
     * Crea un nuevo vehículo.
     *
     * @param vehicleRequestDTO Datos del vehículo a crear.
     * @return Respuesta con los datos del vehículo creado y un estado HTTP 201 (CREATED).
     */
    @PostMapping
    public ResponseEntity<VehicleResponseDTO> createVehicle(@Valid @RequestBody VehicleRequestDTO vehicleRequestDTO){
        VehicleResponseDTO vehicle = vehicleService.createVehicle(vehicleRequestDTO);
        return new ResponseEntity<>(vehicle, HttpStatus.CREATED);
    }

    /**
     * Actualiza un vehículo existente por su ID.
     *
     * @param id                ID del vehículo a actualizar.
     * @param vehicleRequestDTO Datos actualizados del vehículo.
     * @return Respuesta con los datos del vehículo actualizado y un estado HTTP 200 (OK).
     */
    @PutMapping("/{id}")
    public ResponseEntity<VehicleResponseDTO> updateVehicle(@Valid @PathVariable Long id, @RequestBody VehicleRequestDTO vehicleRequestDTO){
        VehicleResponseDTO vehicle = vehicleService.updateVehicle(id, vehicleRequestDTO);
        return ResponseEntity.ok(vehicle);
    }

    /**
     * Elimina un vehículo por su ID.
     *
     * @param id ID del vehículo a eliminar.
     * @return Respuesta con un estado HTTP 204 (NO CONTENT) si la eliminación fue exitosa.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id){
        vehicleService.deleteVehicle(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
