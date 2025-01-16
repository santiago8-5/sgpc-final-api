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

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long idEmployee;

    @Column(name="name", nullable=true, length=100)
    private String name;

    @Column(name="rfc", nullable=true, length=13)
    private String rfc;

    @Column(name="email", nullable=true, length=100)
    private String email;

    @Column(name="hiringDate", nullable=true)
    private LocalDate hiringDate;

    @ManyToOne(fetch=FetchType.LAZY, optional=false)
    @JoinColumn(name="id_position", nullable = false)
    @JsonBackReference("position-employee")
    private Position position;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_category", nullable = false)
    @JsonBackReference("category-employee")
    private Category category;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime created_at;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;


    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference("phone-employee")
    private Set<Phone> phones = new HashSet<>();

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference("account-employee")
    private Set<Account> accounts = new HashSet<>();


    @Transient
    private String employeeType;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return idEmployee != null && idEmployee.equals(employee.idEmployee);
    }

    @Override
    public int hashCode() {
        return idEmployee != null ? idEmployee.hashCode() : 0;
    }


}
