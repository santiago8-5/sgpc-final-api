package com.grupoingenios.sgpc.sgpc_api_final.dto.schedule;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class StageResponseDTO {

    private Long idStage;

    private String name;

    private String description;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

}
