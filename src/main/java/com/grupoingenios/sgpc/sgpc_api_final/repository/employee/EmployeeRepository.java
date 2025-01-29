package com.grupoingenios.sgpc.sgpc_api_final.repository.employee;

import com.grupoingenios.sgpc.sgpc_api_final.dto.employee.EmployeeResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

/**
 * Repositorio para la entidad `Employee`.
 * Proporciona métodos para interactuar con la base de datos, permitiendo realizar operaciones CRUD sobre los empleados,
 * así como consultas personalizadas.
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /**
     * Verifica si existe un empleado con el correo electrónico proporcionado.
     *
     * @param email El correo electrónico del empleado.
     * @return `true` si existe un empleado con el correo electrónico proporcionado, `false` en caso contrario.
     */
    boolean existsByEmail(String email);

    /**
     * Verifica si existe un empleado con el RFC proporcionado, ignorando mayúsculas y minúsculas.
     *
     * @param rfc El RFC del empleado.
     * @return `true` si existe un empleado con el RFC proporcionado, `false` en caso contrario.
     */
    boolean existsByRfcIgnoreCase(String rfc);

    /**
     * Verifica si existe un empleado asociado a la categoría con el ID proporcionado.
     *
     * @param categoryId El ID de la categoría.
     * @return `true` si existe un empleado con la categoría proporcionada, `false` en caso contrario.
     */
    boolean existsByCategory_IdCategory(Long categoryId);

    /**
     * Verifica si existe un empleado asociado al puesto con el ID proporcionado.
     *
     * @param positionId El ID del puesto.
     * @return `true` si existe un empleado con el puesto proporcionado, `false` en caso contrario.
     */
    boolean existsByPosition_IdPosition(Long positionId);


    /**
     * Consulta personalizada que obtiene el ID y el nombre de todos los empleados.
     *
     * @return Una lista de objetos `EmployeeResponseDTO` que contienen el ID y el nombre de los empleados.
     */
    @Query("SELECT new com.grupoingenios.sgpc.sgpc_api_final.dto.employee.EmployeeResponseDTO(e.idEmployee, e.name) " +
            "FROM Employee e")
    List<EmployeeResponseDTO> findAllIdAndName();
}
