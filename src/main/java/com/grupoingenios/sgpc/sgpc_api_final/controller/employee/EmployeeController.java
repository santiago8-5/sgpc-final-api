package com.grupoingenios.sgpc.sgpc_api_final.controller.employee;

import com.grupoingenios.sgpc.sgpc_api_final.dto.employee.EmployeeRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.employee.EmployeeResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.service.employee.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controlador REST para gestionar operaciones relacionadas con empleados.
 * Proporciona endpoints para realizar operaciones CRUD y obtener datos específicos de empleados.
 */
@RestController
@RequestMapping("/api/v1/employees")
@Validated
public class EmployeeController {

    private final EmployeeService employeeService;

    /**
     * Constructor para inyectar el servicio de empleados.
     *
     * @param employeeService Servicio que contiene la lógica de negocio para los empleados.
     */
    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    /**
     * Obtiene la lista de todos los empleados.
     *
     * @return Respuesta con la lista de empleados y un estado HTTP 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees() {
        List<EmployeeResponseDTO> employees = employeeService.getAllEmployees();

        return ResponseEntity.ok(employees);
    }


    /**
     * Obtiene los detalles de un empleado por su ID.
     *
     * @param id ID del empleado que se desea obtener.
     * @return Respuesta con los detalles del empleado y un estado HTTP 200 (OK).
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeById(@PathVariable Long id){
        EmployeeResponseDTO employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    /**
     * Crea un nuevo empleado.
     *
     * @param employeeRequestDTO Datos del empleado a crear.
     * @return Respuesta con los datos del empleado creado y un estado HTTP 201 (CREATED).
     */
    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> createEmployee(@Valid @RequestBody EmployeeRequestDTO employeeRequestDTO){
        EmployeeResponseDTO createdEmployee = employeeService.createEmployee(employeeRequestDTO);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }

    /**
     * Actualiza un empleado existente por su ID.
     *
     * @param id                  ID del empleado a actualizar.
     * @param employeeRequestDTO  Datos actualizados del empleado.
     * @return Respuesta con los datos del empleado actualizado y un estado HTTP 200 (OK).
     */
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(@PathVariable long id, @Valid @RequestBody EmployeeRequestDTO employeeRequestDTO){
        EmployeeResponseDTO updatedEmployee = employeeService.updateEmployee(id, employeeRequestDTO);
        return ResponseEntity.ok(updatedEmployee);
    }

    /**
     * Elimina un empleado por su ID.
     *
     * @param id ID del empleado a eliminar.
     * @return Respuesta con un estado HTTP 204 (NO CONTENT) si la eliminación fue exitosa.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable long id){
        employeeService.deleteEmployeeById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Obtiene una lista con el ID y nombre de todos los empleados.
     *
     * @return Respuesta con una lista que contiene el ID y nombre de los empleados y un estado HTTP 200 (OK).
     */
    @GetMapping("/employees/id-and-name")
    public ResponseEntity<List<EmployeeResponseDTO>> getAllIdAndName() {
        List<EmployeeResponseDTO> employeeIdAndName= employeeService.getAllIdAndName();
        return ResponseEntity.ok(employeeIdAndName);
    }



}