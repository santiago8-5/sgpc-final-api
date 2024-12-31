package com.grupoingenios.sgpc.sgpc_api_final.entity.employee;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
public class PhoneId implements Serializable {
    private String phone;
    private Long employeeId;

    // Constructor para inicializar solo el teléfono, pero en realidad necesitarás también el employeeId
    public PhoneId(String phone, Long employeeId) {
        this.phone = phone;
        this.employeeId = employeeId;
    }
}