package com.grupoingenios.sgpc.sgpc_api_final.dto.schedule;


import com.grupoingenios.sgpc.sgpc_api_final.entity.schedule.StatusActivityAssignment;
import lombok.Data;


@Data
public class ActivityAssignmentRequestDTO {

    private String role;

    private boolean responsible;

    private StatusActivityAssignment status;

    private Long employeeId;

    private Long scheduledActivityId;

}
