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

@RestController
@RequestMapping("/api/v1/employees")
@Validated
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees() {
        List<EmployeeResponseDTO> employees = employeeService.getAllEmployees();

        return ResponseEntity.ok(employees);
    }


    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> getEMployeeById(@PathVariable Long id){
        EmployeeResponseDTO employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> createEmployee(@Valid @RequestBody EmployeeRequestDTO employeeRequestDTO){
        EmployeeResponseDTO createdEmployee = employeeService.createEmployee(employeeRequestDTO);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(@PathVariable long id, @Valid @RequestBody EmployeeRequestDTO employeeRequestDTO){
        EmployeeResponseDTO updatedEmployee = employeeService.updateEmployee(id, employeeRequestDTO);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable long id){
        employeeService.deleteEmployeeById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/employees/id-and-name")
    public ResponseEntity<List<EmployeeResponseDTO>> getAllIdAndName() {
        List<EmployeeResponseDTO> employeeIdAndName= employeeService.getAllIdAndName();
        return ResponseEntity.ok(employeeIdAndName);
    }



}