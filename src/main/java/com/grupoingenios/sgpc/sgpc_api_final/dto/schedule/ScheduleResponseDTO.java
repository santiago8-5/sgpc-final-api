package com.grupoingenios.sgpc.sgpc_api_final.dto.schedule;

import com.grupoingenios.sgpc.sgpc_api_final.entity.schedule.StatusSchedule;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class ScheduleResponseDTO {

    private Long idSchedule;

    private String name;

    private String description;

    private StatusSchedule status;

    /*
    private List<Activity> activities;
     */

    private LocalDateTime created_at;

    private LocalDateTime lastModifiedDate;

}