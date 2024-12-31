package com.grupoingenios.sgpc.sgpc_api_final.controller.schedule;


import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ActivityAssignmentRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ActivityAssignmentResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ScheduledActivityResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.service.schedule.ScheduledActivityService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/scheduled-activities")
@Validated
public class ScheduledActivityController {

    private final ScheduledActivityService scheduledActivityService;

    public ScheduledActivityController(ScheduledActivityService scheduledActivityService) {
        this.scheduledActivityService = scheduledActivityService;
    }

    @GetMapping
    public ResponseEntity<List<ScheduledActivityResponseDTO>> getAllScheduleActivity(){
        List<ScheduledActivityResponseDTO>  scheduledActivities = scheduledActivityService.getAllScheduleActivity();
        return ResponseEntity.ok(scheduledActivities);
    }

    @PostMapping("/{scheduledActivityId}/assignment-employee")
    public ResponseEntity<ActivityAssignmentResponseDTO> createActivityAssignment(@PathVariable Long scheduledActivityId,  @Valid @RequestBody ActivityAssignmentRequestDTO activityAssignmentRequestDTO){
        ActivityAssignmentResponseDTO activityAssignment = scheduledActivityService
                .createActivityAssignment(scheduledActivityId, activityAssignmentRequestDTO);
        return new ResponseEntity<>(activityAssignment, HttpStatus.CREATED);
    }

    @PutMapping("/{scheduledActivityId}/assignment-employee/{activityAssignmentId}")
    public ResponseEntity<ActivityAssignmentResponseDTO> updateActivityAssignment(@PathVariable Long scheduledActivityId, @PathVariable Long activityAssignmentId, @Valid @RequestBody ActivityAssignmentRequestDTO activityAssignmentRequestDTO){
        ActivityAssignmentResponseDTO activityAssignment = scheduledActivityService
                .updateActivityAssignment(scheduledActivityId, activityAssignmentId, activityAssignmentRequestDTO);
        return ResponseEntity.ok(activityAssignment);
    }

    @DeleteMapping("/{scheduledActivityId}/assignment-employee/{activityAssignmentId}")
    public ResponseEntity<Void> deleteActivityAssignment(@PathVariable Long scheduledActivityId, @PathVariable Long activityAssignmentId){
        scheduledActivityService.deleteActivityAssignment(scheduledActivityId, activityAssignmentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
