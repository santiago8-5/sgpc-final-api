package com.grupoingenios.sgpc.sgpc_api_final.controller.machinary;

import com.grupoingenios.sgpc.sgpc_api_final.dto.machinery.MachineryRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.machinery.MachineryResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.service.machinery.MachineryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/machinery")
@Validated
public class MachineryController {

    private final MachineryService machineryService;

    public MachineryController(MachineryService machineryService) {
        this.machineryService = machineryService;
    }

    @GetMapping
    public ResponseEntity<List<MachineryResponseDTO>> getAllMachinery(){
        List<MachineryResponseDTO> machinery = machineryService.getAllMachinery();
        return ResponseEntity.ok(machinery);
    }

    @PostMapping
    public ResponseEntity<MachineryResponseDTO> createMachinery(@Valid @RequestBody MachineryRequestDTO machineryRequestDTO){
        MachineryResponseDTO machinery = machineryService.createMachinery(machineryRequestDTO);
        return new ResponseEntity<>(machinery, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<MachineryResponseDTO> updateMachinery(@Valid @PathVariable Long id, @RequestBody MachineryRequestDTO machineryRequestDTO){
        MachineryResponseDTO machinery = machineryService.updateMachinery(id, machineryRequestDTO);
        return ResponseEntity.ok(machinery);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMachinery(@PathVariable Long id){
        machineryService.deleteMachinery(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}