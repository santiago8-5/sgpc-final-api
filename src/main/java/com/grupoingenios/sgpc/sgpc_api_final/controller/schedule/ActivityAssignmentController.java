package com.grupoingenios.sgpc.sgpc_api_final.controller.schedule;


import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ActivityAssignmentResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.service.schedule.ActivityAssignmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/activity-assignment")
@Validated
public class ActivityAssignmentController {

    private final ActivityAssignmentService activityAssignmentService;

    public ActivityAssignmentController(ActivityAssignmentService activityAssignmentService) {
        this.activityAssignmentService = activityAssignmentService;
    }

    @GetMapping
    public ResponseEntity<List<ActivityAssignmentResponseDTO>> getAllActivityAssignment(){
        List<ActivityAssignmentResponseDTO> activityAssignments = activityAssignmentService
                .getAllActivityAssignment();
        return ResponseEntity.ok(activityAssignments);
    }



}
