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

@RestController
@RequestMapping("/api/v1/positions")
@Validated
public class PositionController {

    private final PositionService positionService;

    public PositionController(PositionService positionService){

        this.positionService = positionService;

    }

    @GetMapping
    public ResponseEntity<List<PositionResponseDTO>> getAllPositions(){
        List<PositionResponseDTO> positions = positionService.getAllPositions();
        return ResponseEntity.ok(positions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PositionResponseDTO> getPositionById(@PathVariable Long id){
        PositionResponseDTO position = positionService.getPositionById(id);
        return ResponseEntity.ok(position);
    }

    @PostMapping
    public ResponseEntity<PositionResponseDTO> createPosition(@Valid @RequestBody PositionRequestDTO positionDTO){
        PositionResponseDTO createdPosition = positionService.createPosition(positionDTO);
        return new ResponseEntity<>(createdPosition, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PositionResponseDTO> updatePosition(@PathVariable long id, @Valid @RequestBody PositionRequestDTO positionDTO){
        PositionResponseDTO updatedPosition = positionService.updatePosition(id, positionDTO);
        return ResponseEntity.ok(updatedPosition);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePosition(@PathVariable Long id){
        positionService.deletePosition(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
