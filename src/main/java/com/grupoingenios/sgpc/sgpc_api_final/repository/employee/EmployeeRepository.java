package com.grupoingenios.sgpc.sgpc_api_final.repository.employee;

import com.grupoingenios.sgpc.sgpc_api_final.entity.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    boolean existsByEmail(String email);
    boolean existsByRfcIgnoreCase(String rfc);
    boolean existsByCategory_IdCategory(Long categoryId);
    boolean existsByPosition_IdPosition(Long positionId);
}
