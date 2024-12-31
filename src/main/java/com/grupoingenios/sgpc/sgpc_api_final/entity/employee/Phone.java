package com.grupoingenios.sgpc.sgpc_api_final.entity.employee;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "phone")
@NoArgsConstructor
@Setter
@Getter
public class Phone {

    @EmbeddedId
    private PhoneId id_phone;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("employeeId") // Mapea la parte de employeId en PhoneId
    @JoinColumn(name="employee_Id")
    @JsonBackReference("phone-employee")
    private Employee employee;


    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime created_at;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;


    // Constructor para facilitar la creación del objeto Phone
    public Phone(String phone, Employee employee) {
        if (phone == null || employee == null) {
            throw new IllegalArgumentException("Phone and Employee must not be null");
        }
        this.id_phone = new PhoneId(phone, employee.getIdEmployee());
        this.employee = employee;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Phone phone = (Phone) o;
        return id_phone != null && id_phone.equals(phone.id_phone);
    }

    @Override
    public int hashCode() {
        return id_phone != null ? id_phone.hashCode() : 0;
    }

}