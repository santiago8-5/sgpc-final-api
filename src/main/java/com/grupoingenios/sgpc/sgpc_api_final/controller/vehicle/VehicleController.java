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

@RestController
@RequestMapping("/api/v1/vehicles")
@Validated
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping
    public ResponseEntity<List<VehicleResponseDTO>> getAllVehicles(){
        List<VehicleResponseDTO> vehicles = vehicleService.getAllVehicles();
        return ResponseEntity.ok(vehicles);
    }

    @PostMapping
    public ResponseEntity<VehicleResponseDTO> createVehicle(@Valid @RequestBody VehicleRequestDTO vehicleRequestDTO){
        VehicleResponseDTO vehicle = vehicleService.createVehicle(vehicleRequestDTO);
        return new ResponseEntity<>(vehicle, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleResponseDTO> updateVehicle(@Valid @PathVariable Long id, @RequestBody VehicleRequestDTO vehicleRequestDTO){
        VehicleResponseDTO vehicle = vehicleService.updateVehicle(id, vehicleRequestDTO);
        return ResponseEntity.ok(vehicle);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id){
        vehicleService.deleteVehicle(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
