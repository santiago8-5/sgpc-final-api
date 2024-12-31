package com.grupoingenios.sgpc.sgpc_api_final.dto.work;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WorkTypeResponseDTO {

    private Long idWorkType;

    private String name;

    private String description;

    private LocalDateTime created_at;

    private LocalDateTime lastModifiedDate;

}
