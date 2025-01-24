package com.grupoingenios.sgpc.sgpc_api_final.controller.employee;

import com.grupoingenios.sgpc.sgpc_api_final.dto.employee.DepartmentRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.employee.DepartmentResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.service.employee.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar operaciones relacionadas con departamentos.
 * Proporciona endpoints para realizar operaciones CRUD sobre los departamentos.
 */
@RestController
@RequestMapping("/api/v1/departments")
@Validated
public class DepartmentController {

    private final DepartmentService departmentService;

    /**
     * Constructor para inyectar el servicio de departamentos.
     *
     * @param departmentService Servicio que contiene la lógica de negocio para los departamentos.
     */
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    /**
     * Obtiene la lista de todos los departamentos.
     *
     * @return Respuesta con la lista de departamentos y un estado HTTP 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<DepartmentResponseDTO>> getAllDepartments() {
        List<DepartmentResponseDTO> departments = departmentService.getAllDepartment();
        return ResponseEntity.ok(departments);
    }

    /**
     * Obtiene los detalles de un departamento por su ID.
     *
     * @param id ID del departamento que se desea obtener.
     * @return Respuesta con los detalles del departamento y un estado HTTP 200 (OK).
     */
    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponseDTO> getDepartmentById(@PathVariable Long id){
        DepartmentResponseDTO department = departmentService.getDepartmentById(id);
        return ResponseEntity.ok(department);
    }

    /**
     * Crea un nuevo departamento.
     *
     * @param departmentDTO Datos del departamento a crear.
     * @return Respuesta con los datos del departamento creado y un estado HTTP 201 (CREATED).
     */
    @PostMapping
    public ResponseEntity<DepartmentResponseDTO> createDepartment(@Valid @RequestBody DepartmentRequestDTO departmentDTO){
        DepartmentResponseDTO createdDepartment = departmentService.createDepartment(departmentDTO);
        return new ResponseEntity<>(createdDepartment, HttpStatus.CREATED);
    }

    /**
     * Actualiza un departamento existente por su ID.
     *
     * @param id            ID del departamento a actualizar.
     * @param departmentDTO Datos actualizados del departamento.
     * @return Respuesta con los datos del departamento actualizado y un estado HTTP 200 (OK).
     */
    @PutMapping("/{id}")
    public ResponseEntity<DepartmentResponseDTO> updateDepartment(@PathVariable Long id, @Valid @RequestBody DepartmentRequestDTO departmentDTO){
        DepartmentResponseDTO updatedDepartment = departmentService.updateDepartment(id, departmentDTO);
        return ResponseEntity.ok(updatedDepartment);
    }

    /**
     * Elimina un departamento por su ID.
     *
     * @param id ID del departamento a eliminar.
     * @return Respuesta con un estado HTTP 204 (NO CONTENT) si la eliminación fue exitosa.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id){
        departmentService.deleteDepartment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}