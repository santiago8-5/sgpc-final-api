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

/**
 * Controlador REST para gestionar operaciones relacionadas con maquinaria.
 * Proporciona endpoints para realizar operaciones CRUD sobre la maquinaria.
 */
@RestController
@RequestMapping("/api/v1/machinery")
@Validated
public class MachineryController {

    private final MachineryService machineryService;

    /**
     * Constructor para inyectar el servicio de maquinaria.
     *
     * @param machineryService Servicio que contiene la lógica de negocio para la maquinaria.
     */
    public MachineryController(MachineryService machineryService) {
        this.machineryService = machineryService;
    }

    /**
     * Obtiene la lista de toda la maquinaria.
     *
     * @return Respuesta con la lista de maquinaria y un estado HTTP 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<MachineryResponseDTO>> getAllMachinery(){
        List<MachineryResponseDTO> machinery = machineryService.getAllMachinery();
        return ResponseEntity.ok(machinery);
    }

    /**
     * Crea una nueva maquinaria.
     *
     * @param machineryRequestDTO Datos de la maquinaria a crear.
     * @return Respuesta con los datos de la maquinaria creada y un estado HTTP 201 (CREATED).
     */
    @PostMapping
    public ResponseEntity<MachineryResponseDTO> createMachinery(@Valid @RequestBody MachineryRequestDTO machineryRequestDTO){
        MachineryResponseDTO machinery = machineryService.createMachinery(machineryRequestDTO);
        return new ResponseEntity<>(machinery, HttpStatus.CREATED);
    }


    /**
     * Actualiza una maquinaria existente por su ID.
     *
     * @param id                  ID de la maquinaria a actualizar.
     * @param machineryRequestDTO Datos actualizados de la maquinaria.
     * @return Respuesta con los datos de la maquinaria actualizada y un estado HTTP 200 (OK).
     */
    @PutMapping("/{id}")
    public ResponseEntity<MachineryResponseDTO> updateMachinery(@Valid @PathVariable Long id, @RequestBody MachineryRequestDTO machineryRequestDTO){
        MachineryResponseDTO machinery = machineryService.updateMachinery(id, machineryRequestDTO);
        return ResponseEntity.ok(machinery);
    }

    /**
     * Elimina una maquinaria por su ID.
     *
     * @param id ID de la maquinaria a eliminar.
     * @return Respuesta con un estado HTTP 204 (NO CONTENT) si la eliminación fue exitosa.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMachinery(@PathVariable Long id){
        machineryService.deleteMachinery(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}