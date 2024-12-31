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

@RestController
@RequestMapping("/api/v1/workTypes")
@Validated
public class WorkTypeController {

    private final WorkTypeService workTypeService;

    public WorkTypeController(WorkTypeService workTypeService) {
        this.workTypeService = workTypeService;
    }

    @GetMapping
    public ResponseEntity<List<WorkTypeResponseDTO>> getAllWorkTypes(){
        List<WorkTypeResponseDTO> workTypes = workTypeService.getAllWorkTypes();
        return ResponseEntity.ok(workTypes);
    }

    @PostMapping
    public ResponseEntity<WorkTypeResponseDTO> createWorkTypes(@Valid @RequestBody WorkTypeRequestDTO workTypeRequestDTO){
        WorkTypeResponseDTO workType = workTypeService.createWorkType(workTypeRequestDTO);

        return new ResponseEntity<>(workType, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkTypeResponseDTO> updateWorkTypes(@Valid @PathVariable Long id, @RequestBody WorkTypeRequestDTO workTypeRequestDTO){
        WorkTypeResponseDTO workType = workTypeService.updateWorkType(id, workTypeRequestDTO);
        return ResponseEntity.ok(workType);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkTypes(@PathVariable Long id){
        workTypeService.deleteWorkType(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
