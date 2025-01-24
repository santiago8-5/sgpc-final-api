package com.grupoingenios.sgpc.sgpc_api_final.entity.employee;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import java.time.LocalDateTime;

/**
 * Entidad que representa un teléfono asociado a un empleado.
 * Un teléfono puede estar vinculado a un único empleado mediante una clave compuesta
 * formada por el número de teléfono y el ID del empleado.
 */
@Entity
@Table(name = "phone")
@NoArgsConstructor
@Setter
@Getter
public class Phone {

    /**
     * Clave primaria compuesta que incluye el número de teléfono y el ID del empleado.
     */
    @EmbeddedId
    private PhoneId id_phone;

    /**
     * Relación Many-to-One con la entidad `Employee`. Un teléfono está asociado a un único empleado.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("employeeId") // Mapea la parte de employeId en PhoneId
    @JoinColumn(name="employee_Id")
    @JsonBackReference("phone-employee")
    private Employee employee;

    /**
     * Fecha en que se crea el teléfono en la base de datos.
     * Este campo es gestionado automáticamente por Spring Data JPA.
     */
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime created_at;

    /**
     * Fecha de la última modificación del teléfono.
     * Este campo es gestionado automáticamente por Spring Data JPA.
     */
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;


    /**
     * Constructor que facilita la creación del objeto `Phone`.
     * Asocia el número de teléfono y el empleado correspondiente a este teléfono.
     *
     * @param phone El número de teléfono.
     * @param employee El empleado al que se asocia el teléfono.
     */
    public Phone(String phone, Employee employee) {
        if (phone == null || employee == null) {
            throw new IllegalArgumentException("Phone and Employee must not be null");
        }
        this.id_phone = new PhoneId(phone, employee.getIdEmployee());
        this.employee = employee;
    }


    /**
     * Compara dos objetos `Phone` para verificar si son iguales.
     *
     * @param o El objeto a comparar.
     * @return `true` si ambos objetos son iguales, de lo contrario `false`.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Phone phone = (Phone) o;
        return id_phone != null && id_phone.equals(phone.id_phone);
    }

    /**
     * Genera el código hash de este objeto `Phone`.
     *
     * @return El código hash generado para este objeto.
     */
    @Override
    public int hashCode() {
        return id_phone != null ? id_phone.hashCode() : 0;
    }

}