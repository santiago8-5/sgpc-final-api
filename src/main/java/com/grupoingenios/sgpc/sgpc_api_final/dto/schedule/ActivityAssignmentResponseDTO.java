package com.grupoingenios.sgpc.sgpc_api_final.dto.schedule;


import com.grupoingenios.sgpc.sgpc_api_final.entity.schedule.StatusActivityAssignment;
import lombok.Data;


import java.time.LocalDateTime;

@Data
public class ActivityAssignmentResponseDTO {


    private Long activityAssignmentId;

    private String role;

    private boolean responsible;

    private StatusActivityAssignment status;

    private String nameEmployee;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;


}
