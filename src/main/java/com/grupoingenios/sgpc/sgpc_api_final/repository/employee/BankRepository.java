package com.grupoingenios.sgpc.sgpc_api_final.repository.employee;

import com.grupoingenios.sgpc.sgpc_api_final.entity.employee.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  BankRepository extends JpaRepository<Bank, Long> {
    boolean existsByNameIgnoreCase(String name);
}
