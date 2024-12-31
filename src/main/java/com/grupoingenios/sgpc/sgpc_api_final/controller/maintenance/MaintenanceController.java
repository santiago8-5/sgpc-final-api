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

@RestController
@RequestMapping("/api/v1/maintenance")
@Validated
public class MaintenanceController {

    private final MaintenanceService maintenanceService;

    public MaintenanceController(MaintenanceService maintenanceService) {
        this.maintenanceService = maintenanceService;
    }

    @GetMapping
    public ResponseEntity<List<MaintenanceResponseDTO>> getAllMaintenance(){
        List<MaintenanceResponseDTO> maintenance = maintenanceService.getAllMaintenance();
        return ResponseEntity.ok(maintenance);
    }

    @PostMapping
    public ResponseEntity<MaintenanceResponseDTO> createMaintenance(@RequestBody @Valid MaintenanceRequestDTO maintenanceRequestDTO){
        MaintenanceResponseDTO maintenance = maintenanceService.createMaintenance(maintenanceRequestDTO);
        return new ResponseEntity<>(maintenance, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaintenanceResponseDTO> createMaintenance(@PathVariable Long id, @RequestBody @Valid MaintenanceRequestDTO maintenanceRequestDTO){
        MaintenanceResponseDTO maintenance = maintenanceService.updateMaintenance(id, maintenanceRequestDTO);
        return ResponseEntity.ok(maintenance);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaintenance(@PathVariable Long id){
        maintenanceService.deleteMaintenance(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
