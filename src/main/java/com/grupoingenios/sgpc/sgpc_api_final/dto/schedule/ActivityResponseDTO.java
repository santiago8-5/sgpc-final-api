package com.grupoingenios.sgpc.sgpc_api_final.dto.schedule;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ActivityResponseDTO {

    private Long idActivity;

    private String name;

    private String description;

    private String stageName;

    private Long idStage;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

}
