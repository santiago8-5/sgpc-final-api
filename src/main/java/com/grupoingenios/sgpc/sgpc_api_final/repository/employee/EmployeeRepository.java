package com.grupoingenios.sgpc.sgpc_api_final.repository.employee;

import com.grupoingenios.sgpc.sgpc_api_final.dto.employee.EmployeeResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    boolean existsByEmail(String email);
    boolean existsByRfcIgnoreCase(String rfc);
    boolean existsByCategory_IdCategory(Long categoryId);
    boolean existsByPosition_IdPosition(Long positionId);

    @Query("SELECT new com.grupoingenios.sgpc.sgpc_api_final.dto.employee.EmployeeResponseDTO(e.idEmployee, e.name) " +
            "FROM Employee e")
    List<EmployeeResponseDTO> findAllIdAndName();
}
