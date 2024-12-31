package com.grupoingenios.sgpc.sgpc_api_final.dto.employee;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class AccountResponseDTO {

    private Long bankId;

    private String accountNumber;

    private String nameBank;

    private LocalDateTime created_at;

    private LocalDateTime lastModifiedDate;
}
