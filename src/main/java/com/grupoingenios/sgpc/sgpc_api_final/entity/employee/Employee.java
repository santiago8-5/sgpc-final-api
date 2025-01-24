package com.grupoingenios.sgpc.sgpc_api_final.entity.employee;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Representa a un empleado en la organización.
 * Es la clase base para los tipos de empleados como 'PLANTA' y 'OBRA'.
 * Contiene la información básica del empleado, como nombre, RFC, correo electrónico y fecha de contratación.
 * También contiene las relaciones con otras entidades, como la posición, categoría, teléfonos y cuentas bancarias asociadas al empleado.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "employeeType", discriminatorType = DiscriminatorType.STRING)
@Table(name="employee")
@EntityListeners(AuditingEntityListener.class)
public class Employee {

    /**
     * Identificador único del empleado.
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long idEmployee;

    /**
     * Nombre completo del empleado.
     */
    @Column(name="name", nullable=true, length=100)
    private String name;

    /**
     * RFC del empleado.
     */
    @Column(name="rfc", nullable=true, length=13)
    private String rfc;

    /**
     * Correo electrónico del empleado.
     */
    @Column(name="email", nullable=true, length=100)
    private String email;

    /**
     * Fecha de contratación del empleado.
     */
    @Column(name="hiringDate", nullable=true)
    private LocalDate hiringDate;

    /**
     * Relación muchos a uno con la entidad `Position`.
     * Un empleado tiene una posición asignada en la empresa.
     */
    @ManyToOne(fetch=FetchType.LAZY, optional=false)
    @JoinColumn(name="id_position", nullable = false)
    @JsonBackReference("position-employee")
    private Position position;

    /**
     * Relación muchos a uno con la entidad `Category`.
     * Un empleado tiene una categoría asignada en la organización.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_category", nullable = false)
    @JsonBackReference("category-employee")
    private Category category;

    /**
     * Fecha de creación del registro del empleado, gestionada automáticamente por Spring Data JPA.
     */
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime created_at;

    /**
     * Fecha de última modificación del registro del empleado, gestionada automáticamente por Spring Data JPA.
     */
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    /**
     * Relación uno a muchos con la entidad `Phone`.
     * Un empleado puede tener varios números de teléfono asociados.
     */
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference("phone-employee")
    private Set<Phone> phones = new HashSet<>();

    /**
     * Relación uno a muchos con la entidad `Account`.
     * Un empleado puede tener varias cuentas bancarias asociadas.
     */
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference("account-employee")
    private Set<Account> accounts = new HashSet<>();

    /**
     * Atributo transitorio utilizado para almacenar el tipo de empleado.
     * Este valor no se almacena en la base de datos.
     */
    @Transient
    private String employeeType;

    /**
     * Método para obtener el tipo de empleado basado en la subclase.
     * @return "PLANTA" si el empleado es de tipo `PlantEmployee`, "OBRA" si el empleado es de tipo `ConstructionWorker`.
     */
    // Método para obtener el tipo de empleado basado en la subclase
    public String getEmployeeType() {
        if (this instanceof PlantEmployee) {
            return "PLANTA";
        }
        if (this instanceof ConstructionWorker) {
            return "OBRA";
        }
        return "DESCONOCIDO";
    }

    /**
     * Sobrescribe el método `equals` para comparar empleados por su identificador.
     * @param o Objeto con el que se va a comparar.
     * @return `true` si los empleados tienen el mismo identificador, de lo contrario `false`.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return idEmployee != null && idEmployee.equals(employee.idEmployee);
    }

    /**
     * Sobrescribe el método `hashCode` para generar el código hash basado en el identificador del empleado.
     * @return Código hash del empleado.
     */
    @Override
    public int hashCode() {
        return idEmployee != null ? idEmployee.hashCode() : 0;
    }


}
