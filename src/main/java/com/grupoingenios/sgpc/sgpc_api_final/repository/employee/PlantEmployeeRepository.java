package com.grupoingenios.sgpc.sgpc_api_final.repository.employee;

import com.grupoingenios.sgpc.sgpc_api_final.entity.employee.PlantEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantEmployeeRepository extends JpaRepository<PlantEmployee, Long> {

    // Comprobar si hay un departamento usado por un empleado
    boolean existsByDepartment_IdDepartment(Long departmentId);

}
