package com.grupoingenios.sgpc.sgpc_api_final.dto.schedule;

import com.grupoingenios.sgpc.sgpc_api_final.entity.schedule.StatusScheduledActivity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
public class ScheduledActivityResponseDTO {

    private Long scheduledActivityId;

    private LocalDate estimatedStartDate;

    private LocalDate estimatedEndDate;

    private LocalDate actualStartDate;

    private LocalDate actualEndDate;

    private int priority;

    private StatusScheduledActivity status;

    private String nameActivity;

    //private String nameSchedule;

    //private LocalDateTime createdDate;

    //private LocalDateTime localDateTime;
}
