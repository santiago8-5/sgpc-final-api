package com.grupoingenios.sgpc.sgpc_api_final.controller.work;

import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ScheduleRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ScheduleResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.work.ClientWorkRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.work.ClientWorkResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.work.WorkRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.work.WorkResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.service.work.WorkService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/works")
@Validated
public class WorkController {

    private final WorkService workService;

    public WorkController(WorkService workService) {
        this.workService = workService;
    }

    @GetMapping
    public ResponseEntity<List<WorkResponseDTO>> getAllWorks(){
        List<WorkResponseDTO> works = workService.getAllWorks();
        return ResponseEntity.ok(works);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkResponseDTO> getWorkById(@PathVariable long id){
        WorkResponseDTO work = workService.getWorkById(id);
        return ResponseEntity.ok(work);
    }


    @PostMapping
    public ResponseEntity<WorkResponseDTO> createWork(@Valid @RequestBody WorkRequestDTO workRequestDTO){
        WorkResponseDTO work = workService.createWork(workRequestDTO);
        return new ResponseEntity<>(work, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkResponseDTO> updateWork(@Valid @PathVariable Long id, @RequestBody WorkRequestDTO workRequestDTO){
        WorkResponseDTO work = workService.updateWork(id, workRequestDTO);
        return ResponseEntity.ok(work);
    }


    @PostMapping("/{idWork}/schedule")
    public ResponseEntity<ScheduleResponseDTO> createAndAssignSchedule(@PathVariable Long idWork, @Valid @RequestBody ScheduleRequestDTO scheduleRequestDTO){
        ScheduleResponseDTO schedule = workService.createAndAssignSchedule(idWork, scheduleRequestDTO);
        return new ResponseEntity<>(schedule, HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWork(@PathVariable Long id){
        workService.deleteWork(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    // Crear un ClientWork
    @PostMapping("/{idWork}/work-client")
    public ResponseEntity<ClientWorkResponseDTO> assignClientToWork(@PathVariable Long idWork, @RequestBody @Valid ClientWorkRequestDTO clientWorkRequestDTO){
        ClientWorkResponseDTO clientToWork = workService.assignClientToWork(idWork, clientWorkRequestDTO);
        return new ResponseEntity<>(clientToWork, HttpStatus.CREATED);
    }

    // actualizar un ClientWork
    @PutMapping("/{idWork}/work-client/{clientWorkId}")
    public ResponseEntity<ClientWorkResponseDTO> updateClientWork(@PathVariable Long idWork, @PathVariable Long clientWorkId, @RequestBody @Valid ClientWorkRequestDTO clientWorkRequestDTO){
        ClientWorkResponseDTO clientToWork = workService.updateClientWork(idWork, clientWorkId,  clientWorkRequestDTO);
        return ResponseEntity.ok(clientToWork);

    }

    // Delete un clientWork
    @DeleteMapping("/{idWork}/work-client/{clientWorkId}")
    public ResponseEntity<ClientWorkResponseDTO> deleteClientWork(@PathVariable Long idWork, @PathVariable Long clientWorkId){
        workService.deleteClientWork(clientWorkId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // asignar un proveedor a una obra
    @PostMapping("/{workId}/suppliers/{supplierId}")
    public ResponseEntity<Void> assignSupplierToWork(
            @PathVariable Long workId,
            @PathVariable Long supplierId) {
        workService.assignSupplierToWork(workId, supplierId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
