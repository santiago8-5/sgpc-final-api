package com.grupoingenios.sgpc.sgpc_api_final.controller.work;

import com.grupoingenios.sgpc.sgpc_api_final.dto.work.WorkTypeRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.work.WorkTypeResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.service.work.WorkTypeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controlador REST para gestionar operaciones relacionadas con los tipos de obra.
 * Proporciona endpoints para realizar operaciones CRUD sobre los tipos de obra.
 */
@RestController
@RequestMapping("/api/v1/workTypes")
@Validated
public class WorkTypeController {

    private final WorkTypeService workTypeService;

    /**
     * Constructor para inyectar el servicio de tipos de obra.
     *
     * @param workTypeService Servicio que contiene la lógica de negocio para los tipos de obra.
     */
    public WorkTypeController(WorkTypeService workTypeService) {
        this.workTypeService = workTypeService;
    }

    /**
     * Obtiene la lista de todos los tipos de obra.
     *
     * @return Lista de tipos de obra y un estado HTTP 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<WorkTypeResponseDTO>> getAllWorkTypes(){
        List<WorkTypeResponseDTO> workTypes = workTypeService.getAllWorkTypes();
        return ResponseEntity.ok(workTypes);
    }

    /**
     * Crea un nuevo tipo de obra.
     *
     * @param workTypeRequestDTO Datos del tipo de obra a crear.
     * @return Tipo de obra creado y un estado HTTP 201 (CREATED).
     */
    @PostMapping
    public ResponseEntity<WorkTypeResponseDTO> createWorkTypes(@Valid @RequestBody WorkTypeRequestDTO workTypeRequestDTO){
        WorkTypeResponseDTO workType = workTypeService.createWorkType(workTypeRequestDTO);

        return new ResponseEntity<>(workType, HttpStatus.CREATED);
    }

    /**
     * Actualiza un tipo de obra existente por su ID.
     *
     * @param id                 ID del tipo de obra.
     * @param workTypeRequestDTO Datos actualizados del tipo de obra.
     * @return Tipo de obra actualizado y un estado HTTP 200 (OK).
     */
    @PutMapping("/{id}")
    public ResponseEntity<WorkTypeResponseDTO> updateWorkTypes(@Valid @PathVariable Long id, @RequestBody WorkTypeRequestDTO workTypeRequestDTO){
        WorkTypeResponseDTO workType = workTypeService.updateWorkType(id, workTypeRequestDTO);
        return ResponseEntity.ok(workType);
    }

    /**
     * Elimina un tipo de obra por su ID.
     *
     * @param id ID del tipo de obra.
     * @return Estado HTTP 204 (NO CONTENT).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkTypes(@PathVariable Long id){
        workTypeService.deleteWorkType(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
