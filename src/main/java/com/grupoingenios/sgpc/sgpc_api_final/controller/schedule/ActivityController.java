package com.grupoingenios.sgpc.sgpc_api_final.controller.schedule;


import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ActivityRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ActivityResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.service.schedule.ActivityService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/activities")
@Validated
public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping
    public ResponseEntity<List<ActivityResponseDTO>> getAllActivity(){
        List<ActivityResponseDTO> activities = activityService.getAllActivity();
        return ResponseEntity.ok(activities);
    }

    @PostMapping
    public ResponseEntity<ActivityResponseDTO> createActivity(@RequestBody @Valid ActivityRequestDTO activityRequestDTO){
        ActivityResponseDTO activity = activityService.createActivity(activityRequestDTO);
        return new ResponseEntity<>(activity, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActivityResponseDTO> updateActivity(@PathVariable Long id, @Valid @RequestBody ActivityRequestDTO activityRequestDTO){
        ActivityResponseDTO activity = activityService.updateActivity(id, activityRequestDTO);
        return ResponseEntity.ok(activity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable Long id){
        activityService.deleteActivity(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
