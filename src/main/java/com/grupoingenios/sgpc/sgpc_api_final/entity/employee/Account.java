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

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "account")
@EntityListeners(AuditingEntityListener.class)
public class Account {

    @EmbeddedId
    private AccountId id_account = new AccountId();

    @Column(name = "accountNumber", length = 25)
    private String accountNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("bankId")
    @JoinColumn(name = "bank_Id")
    @JsonBackReference("account-bank")
    private Bank bank;


    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("employeeId")
    @JoinColumn(name = "employee_Id")
    @JsonBackReference("account-employee")
    private Employee employee;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime created_at;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;


    // Constructor específico para facilitar la creación de Account en associateAccounts
    public Account(String accountNumber, Bank bank, Employee employee) {
        this.accountNumber = accountNumber;
        this.bank = bank;
        this.employee = employee;

        // Configuramos la clave compuesta
        this.id_account.setBankId(bank.getIdBank());
        this.id_account.setEmployeeId(employee.getIdEmployee());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id_account != null && id_account.equals(account.id_account);
    }

    @Override
    public int hashCode() {
        return id_account != null ? id_account.hashCode() : 0;
    }

}
