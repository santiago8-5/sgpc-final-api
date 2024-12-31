package com.grupoingenios.sgpc.sgpc_api_final.dto.employee;

import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;

@Getter
@Setter
public class DepartmentResponseDTO {

    private Long idDepartment;

    private String name;

    private String description;

    private String email;

    private LocalDateTime created_at;

    private LocalDateTime lastModifiedDate;

}
