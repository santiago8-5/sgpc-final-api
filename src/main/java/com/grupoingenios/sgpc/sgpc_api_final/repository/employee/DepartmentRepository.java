package com.grupoingenios.sgpc.sgpc_api_final.repository.employee;

import com.grupoingenios.sgpc.sgpc_api_final.entity.employee.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    boolean existsByNameIgnoreCase(String name);
}