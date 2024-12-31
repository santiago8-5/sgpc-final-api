package com.grupoingenios.sgpc.sgpc_api_final.dto.work;

import com.grupoingenios.sgpc.sgpc_api_final.entity.work.Role;
import com.grupoingenios.sgpc.sgpc_api_final.entity.work.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
public class ClientWorkResponseDTO {

    private Long id;

    private String nameClient;

    private String nameWork;

    private Role role;

    private LocalDateTime assignedAt;

    private Status status;

    private LocalDateTime createdAt;

    private LocalDateTime lastModifiedAt;

}
