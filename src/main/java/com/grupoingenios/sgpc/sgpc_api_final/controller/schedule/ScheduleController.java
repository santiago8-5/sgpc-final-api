package com.grupoingenios.sgpc.sgpc_api_final.controller.schedule;

import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ScheduleRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ScheduleResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ScheduledActivityRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ScheduledActivityResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.service.schedule.ScheduleService;
import com.grupoingenios.sgpc.sgpc_api_final.service.schedule.ScheduledActivityService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/schedules")
@Validated
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final ScheduledActivityService scheduledActivityService;

    public ScheduleController(ScheduleService scheduleService, ScheduledActivityService scheduledActivityService) {
        this.scheduleService = scheduleService;
        this.scheduledActivityService = scheduledActivityService;
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponseDTO>> getAllSchedules(){
        List<ScheduleResponseDTO> schedules = scheduleService.getAllSchedules();
        return ResponseEntity.ok(schedules);
    }

    @GetMapping("{id}")
    public ResponseEntity<ScheduleResponseDTO> getScheduleById(@PathVariable Long id){
        ScheduleResponseDTO schedule = scheduleService.getScheduleById(id);
        return ResponseEntity.ok(schedule);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDTO> updateSchedule(@PathVariable Long id, @Valid @RequestBody ScheduleRequestDTO scheduleRequestDTO ){
        ScheduleResponseDTO schedule = scheduleService.updateSchedule(id, scheduleRequestDTO);
        return ResponseEntity.ok(schedule);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id){
        scheduleService.deleteSchedule(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    // Scheduled activities
    @GetMapping("/{scheduleId}/scheduled-activities")
    public ResponseEntity<List<ScheduledActivityResponseDTO>> getScheduledActivities(@PathVariable Long scheduleId) {
        List<ScheduledActivityResponseDTO> activities = scheduledActivityService.getScheduledActivitiesBySchedule(scheduleId);
        return ResponseEntity.ok(activities);
    }

    //Obtener un ScheduledActivity por id
    @GetMapping("/{scheduleId}/activities/{activityId}")
    public ResponseEntity<ScheduledActivityResponseDTO> getScheduledActivityById(
            @PathVariable Long scheduleId,
            @PathVariable Long activityId) {
        ScheduledActivityResponseDTO activity = scheduleService.getScheduledActivityById(scheduleId, activityId);
        return ResponseEntity.ok(activity);
    }

    // Crear un ScheduledActivity
    @PostMapping("/{scheduleId}/scheduled-activities")
    public ResponseEntity<ScheduledActivityResponseDTO> createScheduledActivity(@PathVariable Long scheduleId, @Valid @RequestBody ScheduledActivityRequestDTO scheduledActivityRequestDTO){
        ScheduledActivityResponseDTO scheduledActivity = scheduleService.createAndAssignScheduledActivity(scheduleId, scheduledActivityRequestDTO);
        return new ResponseEntity<>(scheduledActivity, HttpStatus.CREATED);
    }

    // Actualizar un scheduledActivity
    @PutMapping("/{scheduleId}/scheduled-activities/{scheduledActivityId}")
    public ResponseEntity<ScheduledActivityResponseDTO> updateScheduledActivity(@PathVariable Long scheduleId, @PathVariable Long scheduledActivityId,
                                                                                @Valid @RequestBody ScheduledActivityRequestDTO scheduledActivityRequestDTO) {
        ScheduledActivityResponseDTO scheduledActivity = scheduleService.updateScheduleActivity(scheduleId, scheduledActivityId, scheduledActivityRequestDTO);
        return ResponseEntity.ok(scheduledActivity);
    }

    // Eliminar un scheduledActivity
    @DeleteMapping("/{scheduleId}/scheduled-activities/{scheduledActivityId}")
    public ResponseEntity<Void> deleteScheduledActivity(@PathVariable Long scheduleId, @PathVariable Long scheduledActivityId){
        scheduleService.deleteScheduledActivity(scheduleId, scheduledActivityId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}