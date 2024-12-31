package com.grupoingenios.sgpc.sgpc_api_final.entity.employee;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
public class AccountId implements Serializable {
    private Long bankId;
    private Long employeeId;

    public AccountId(Long bankId, Long employeeId){
        this.bankId = bankId;
        this.employeeId = employeeId;
    }
}
