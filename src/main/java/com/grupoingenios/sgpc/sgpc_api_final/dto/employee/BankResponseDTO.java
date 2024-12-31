package com.grupoingenios.sgpc.sgpc_api_final.dto.employee;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Setter
@Getter
public class BankResponseDTO {

    private Long idBank;

    private String name;

    private LocalDateTime created_at;

    private LocalDateTime lastModifiedDate;

}
