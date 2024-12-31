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

@RestController
@RequestMapping("/api/v1/stages")
@Validated
public class StageController {
    private final StageService stageService;

    public  StageController(StageService stageService) {
        this.stageService = stageService;
    }

    @GetMapping
    public ResponseEntity<List<StageResponseDTO>> getAllStage(){
        List<StageResponseDTO> activities = stageService.getAllStage();
        return ResponseEntity.ok(activities);
    }

    @PostMapping
    public ResponseEntity<StageResponseDTO> createStage(@RequestBody @Valid StageRequestDTO stageRequestDTO){
        StageResponseDTO stage = stageService.createStage(stageRequestDTO);
        return new ResponseEntity<>(stage, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StageResponseDTO> updateStage(@PathVariable Long id, @Valid @RequestBody StageRequestDTO stageRequestDTO){
        StageResponseDTO stage = stageService.updateStage(id, stageRequestDTO);
        return ResponseEntity.ok(stage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable Long id){
        stageService.deleteStage(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
