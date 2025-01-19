package com.grupoingenios.sgpc.sgpc_api_final.dto.employee;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
public class EmployeeResponseDTO {

    private Long idEmployee;
    private String name;
    private String rfc;
    private String email;
    private LocalDate hiringDate;
    private String employeeType;
    private String positionName;
    private String categoryName;
    private Set<AccountResponseDTO> accounts;
    private Set<PhoneResponseDTO> phones;

    public EmployeeResponseDTO(Long idEmployee, String name) {
        this.idEmployee = idEmployee;
        this.name = name;
    }
}
