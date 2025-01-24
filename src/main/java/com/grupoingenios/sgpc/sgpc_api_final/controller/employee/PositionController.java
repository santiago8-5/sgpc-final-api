package com.grupoingenios.sgpc.sgpc_api_final.controller.employee;

import com.grupoingenios.sgpc.sgpc_api_final.dto.employee.PositionRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.employee.PositionResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.service.employee.PositionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controlador REST para gestionar operaciones relacionadas con posiciones.
 * Proporciona endpoints para realizar operaciones CRUD sobre las posiciones.
 */
@RestController
@RequestMapping("/api/v1/positions")
@Validated
public class PositionController {

    private final PositionService positionService;

    /**
     * Constructor para inyectar el servicio de posiciones.
     *
     * @param positionService Servicio que contiene la lógica de negocio para las posiciones.
     */
    public PositionController(PositionService positionService){
        this.positionService = positionService;
    }

    /**
     * Obtiene la lista de todas las posiciones.
     *
     * @return Respuesta con la lista de posiciones y un estado HTTP 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<PositionResponseDTO>> getAllPositions(){
        List<PositionResponseDTO> positions = positionService.getAllPositions();
        return ResponseEntity.ok(positions);
    }

    /**
     * Obtiene los detalles de una posición por su ID.
     *
     * @param id ID de la posición que se desea obtener.
     * @return Respuesta con los detalles de la posición y un estado HTTP 200 (OK).
     */
    @GetMapping("/{id}")
    public ResponseEntity<PositionResponseDTO> getPositionById(@PathVariable Long id){
        PositionResponseDTO position = positionService.getPositionById(id);
        return ResponseEntity.ok(position);
    }

    /**
     * Crea una nueva posición.
     *
     * @param positionDTO Datos de la posición a crear.
     * @return Respuesta con los datos de la posición creada y un estado HTTP 201 (CREATED).
     */
    @PostMapping
    public ResponseEntity<PositionResponseDTO> createPosition(@Valid @RequestBody PositionRequestDTO positionDTO){
        PositionResponseDTO createdPosition = positionService.createPosition(positionDTO);
        return new ResponseEntity<>(createdPosition, HttpStatus.CREATED);
    }

    /**
     * Actualiza una posición existente por su ID.
     *
     * @param id          ID de la posición a actualizar.
     * @param positionDTO Datos actualizados de la posición.
     * @return Respuesta con los datos de la posición actualizada y un estado HTTP 200 (OK).
     */
    @PutMapping("/{id}")
    public ResponseEntity<PositionResponseDTO> updatePosition(@PathVariable long id, @Valid @RequestBody PositionRequestDTO positionDTO){
        PositionResponseDTO updatedPosition = positionService.updatePosition(id, positionDTO);
        return ResponseEntity.ok(updatedPosition);
    }

    /**
     * Elimina una posición por su ID.
     *
     * @param id ID de la posición a eliminar.
     * @return Respuesta con un estado HTTP 204 (NO CONTENT) si la eliminación fue exitosa.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePosition(@PathVariable Long id){
        positionService.deletePosition(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
