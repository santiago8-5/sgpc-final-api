package com.grupoingenios.sgpc.sgpc_api_final.entity.employee;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

/**
 * Representa una cuenta asociada a un empleado en un banco.
 * Esta entidad incluye información relevante sobre la cuenta, el banco asociado y el empleado.
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "account")
@EntityListeners(AuditingEntityListener.class)
public class Account {

    /**
     * Identificador compuesto que incluye los ID del banco y del empleado.
     */
    @EmbeddedId
    private AccountId id_account = new AccountId();

    /**
     * Número de la cuenta.
     */
    @Column(name = "accountNumber", length = 25)
    private String accountNumber;

    /**
     * Banco asociado a la cuenta.
     * Relación ManyToOne con la entidad Bank.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("bankId")
    @JoinColumn(name = "bank_Id")
    @JsonBackReference("account-bank")
    private Bank bank;

    /**
     * Empleado asociado a la cuenta.
     * Relación ManyToOne con la entidad Employee.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("employeeId")
    @JoinColumn(name = "employee_Id")
    @JsonBackReference("account-employee")
    private Employee employee;

    /**
     * Fecha de creación de la cuenta.
     * Esta propiedad es manejada automáticamente por Spring Data JPA.
     */
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime created_at;

    /**
     * Fecha de la última modificación de la cuenta.
     * Esta propiedad es manejada automáticamente por Spring Data JPA.
     */
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;


    /**
     * Constructor que inicializa la cuenta con los valores correspondientes.
     * @param accountNumber Número de la cuenta.
     * @param bank Banco asociado a la cuenta.
     * @param employee Empleado asociado a la cuenta.
     */
    public Account(String accountNumber, Bank bank, Employee employee) {
        this.accountNumber = accountNumber;
        this.bank = bank;
        this.employee = employee;

        // Configuramos la clave compuesta
        this.id_account.setBankId(bank.getIdBank());
        this.id_account.setEmployeeId(employee.getIdEmployee());
    }

    /**
     * Compara la cuenta con otro objeto.
     * @param o Objeto a comparar.
     * @return true si los objetos son iguales, false si no lo son.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id_account != null && id_account.equals(account.id_account);
    }

    /**
     * Calcula el código hash de la cuenta.
     * @return Código hash de la cuenta.
     */
    @Override
    public int hashCode() {
        return id_account != null ? id_account.hashCode() : 0;
    }

}
