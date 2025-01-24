package com.grupoingenios.sgpc.sgpc_api_final.controller.schedule;

import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ActivityRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ActivityResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.StageRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.StageResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.service.schedule.StageService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controlador REST para gestionar operaciones relacionadas con etapas (stages).
 * Proporciona endpoints para realizar operaciones CRUD sobre las etapas.
 */
@RestController
@RequestMapping("/api/v1/stages")
@Validated
public class StageController {
    private final StageService stageService;


    /**
     * Constructor para inyectar el servicio de etapas.
     *
     * @param stageService Servicio que contiene la lógica de negocio para las etapas.
     */
    public  StageController(StageService stageService) {
        this.stageService = stageService;
    }

    /**
     * Obtiene la lista de todas las etapas.
     *
     * @return Respuesta con la lista de etapas y un estado HTTP 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<StageResponseDTO>> getAllStage(){
        List<StageResponseDTO> activities = stageService.getAllStage();
        return ResponseEntity.ok(activities);
    }

    /**
     * Crea una nueva etapa.
     *
     * @param stageRequestDTO Datos de la etapa a crear.
     * @return Respuesta con los datos de la etapa creada y un estado HTTP 201 (CREATED).
     */
    @PostMapping
    public ResponseEntity<StageResponseDTO> createStage(@RequestBody @Valid StageRequestDTO stageRequestDTO){
        StageResponseDTO stage = stageService.createStage(stageRequestDTO);
        return new ResponseEntity<>(stage, HttpStatus.CREATED);
    }

    /**
     * Actualiza una etapa existente por su ID.
     *
     * @param id              ID de la etapa a actualizar.
     * @param stageRequestDTO Datos actualizados de la etapa.
     * @return Respuesta con los datos de la etapa actualizada y un estado HTTP 200 (OK).
     */
    @PutMapping("/{id}")
    public ResponseEntity<StageResponseDTO> updateStage(@PathVariable Long id, @Valid @RequestBody StageRequestDTO stageRequestDTO){
        StageResponseDTO stage = stageService.updateStage(id, stageRequestDTO);
        return ResponseEntity.ok(stage);
    }

    /**
     * Elimina una etapa por su ID.
     *
     * @param id ID de la etapa a eliminar.
     * @return Respuesta con un estado HTTP 204 (NO CONTENT) si la eliminación fue exitosa.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable Long id){
        stageService.deleteStage(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
