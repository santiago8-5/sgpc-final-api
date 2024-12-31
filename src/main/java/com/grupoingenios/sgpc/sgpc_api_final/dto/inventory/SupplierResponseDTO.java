package com.grupoingenios.sgpc.sgpc_api_final.dto.inventory;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class SupplierResponseDTO {

    private Long id_supplier;

    private String name;

    private String address;

    private String phone;

    private String email;

    private String rfc;

    private LocalDateTime created_at;

    private LocalDateTime lastModifiedDate;

}
