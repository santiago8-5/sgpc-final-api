package com.grupoingenios.sgpc.sgpc_api_final.entity.employee;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

/**
 * Clase que representa la clave primaria compuesta para la entidad Account.
 * Se utiliza para identificar de manera única una cuenta asociada a un empleado en un banco.
 */
@Embeddable
@Data
@NoArgsConstructor
public class AccountId implements Serializable {

    /**
     * Identificador del banco asociado a la cuenta.
     */
    private Long bankId;

    /**
     * Identificador del empleado asociado a la cuenta.
     */
    private Long employeeId;

    /**
     * Constructor que inicializa los identificadores del banco y del empleado.
     *
     * @param bankId Identificador del banco.
     * @param employeeId Identificador del empleado.
     */
    public AccountId(Long bankId, Long employeeId){
        this.bankId = bankId;
        this.employeeId = employeeId;
    }

}
