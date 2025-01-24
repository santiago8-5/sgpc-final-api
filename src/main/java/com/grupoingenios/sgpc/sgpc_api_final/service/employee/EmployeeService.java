package com.grupoingenios.sgpc.sgpc_api_final.service.employee;

import com.grupoingenios.sgpc.sgpc_api_final.dto.employee.*;
import com.grupoingenios.sgpc.sgpc_api_final.entity.employee.*;
import com.grupoingenios.sgpc.sgpc_api_final.exception.BadRequestException;
import com.grupoingenios.sgpc.sgpc_api_final.exception.EntityInUseException;
import com.grupoingenios.sgpc.sgpc_api_final.exception.ResourceNotFoundException;
import com.grupoingenios.sgpc.sgpc_api_final.mapper.employee.EmployeeMapper;
import com.grupoingenios.sgpc.sgpc_api_final.repository.employee.*;
import com.grupoingenios.sgpc.sgpc_api_final.repository.maintenance.MaintenanceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.grupoingenios.sgpc.sgpc_api_final.constants.AppConstant.*;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final PositionRepository positionRepository;
    private final BankRepository bankRepository;
    private final DepartmentRepository departmentRepository;
    private final CategoryRepository categoryRepository;
    private final MaintenanceRepository maintenanceRepository;


    public EmployeeService(EmployeeRepository employeeRepository, PositionRepository positionRepository,
                           EmployeeMapper employeeMapper, BankRepository bankRepository,
                           DepartmentRepository departmentRepository, CategoryRepository categoryRepository,
                           MaintenanceRepository maintenanceRepository)  {

        this.employeeRepository = employeeRepository;
        this.positionRepository = positionRepository;
        this.employeeMapper = employeeMapper;
        this.bankRepository = bankRepository;
        this.departmentRepository = departmentRepository;
        this.categoryRepository = categoryRepository;
        this.maintenanceRepository = maintenanceRepository;
    }

    @Transactional(readOnly = true)
    public List<EmployeeResponseDTO> getAllEmployees(){
        return employeeRepository.findAll()
                .stream()
                .map(this::mapToSpecificDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<EmployeeResponseDTO>  getAllIdAndName() {
        return employeeRepository.findAllIdAndName();
    }


    @Transactional
    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO employeeRequestDTO) {
        validateEmployeeEmail(employeeRequestDTO.getEmail());

        Position position = getPositionById(employeeRequestDTO.getPositionId());
        Category category = getCategoryById(employeeRequestDTO.getCategoryId());

        Employee employee = switch (employeeRequestDTO) {
            case PlantEmployeeRequestDTO plantEmployeeRequestDTO -> {
                PlantEmployee plantEmployee = employeeMapper.toPlantEmployeeEntity(plantEmployeeRequestDTO);

                Department department = getDepartmentById(plantEmployeeRequestDTO.getDepartmentId());
                plantEmployee.setDepartment(department);

                yield plantEmployee;
            }
            case ConstructionWorkerRequestDTO constructionWorkerRequestDTO ->
                    employeeMapper.toConstructionWorkerEntity(constructionWorkerRequestDTO);

            default -> throw new BadRequestException("Tipo de empleado no soportado");
        };

        employee.setPosition(position);
        employee.setCategory(category);
        associatePhones(employee, employeeRequestDTO.getPhones());
        associateAccounts(employee, employeeRequestDTO.getAccounts());

        Employee employeeCreated = employeeRepository.save(employee);

        return employeeMapper.toResponseDTO(employeeCreated);
    }




    @Transactional
    public EmployeeResponseDTO updateEmployee(Long id, EmployeeRequestDTO employeeRequestDTO) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(EMPLOYEE_NOT_FOUND));

        Position position = getPositionById(employeeRequestDTO.getPositionId());
        Category category = getCategoryById(employeeRequestDTO.getCategoryId());

        existingEmployee.setName(employeeRequestDTO.getName());
        existingEmployee.setRfc(employeeRequestDTO.getRfc());
        existingEmployee.setEmail(employeeRequestDTO.getEmail());
        existingEmployee.setHiringDate(employeeRequestDTO.getHiringDate());
        //existingEmployee.setEmployeeType(employeeRequestDTO.getEmployeeType());
        existingEmployee.setPosition(position);
        existingEmployee.setCategory(category);

        // Actualizar teléfonos
        associatePhones(existingEmployee, employeeRequestDTO.getPhones());

        // Actualizar cuentas bancarias
        associateAccounts(existingEmployee, employeeRequestDTO.getAccounts());

        Employee updatedEmployee = employeeRepository.save(existingEmployee);

        return employeeMapper.toResponseDTO(updatedEmployee);
    }

    @Transactional
    public void deleteEmployeeById(Long id){
        if (!employeeRepository.existsById(id)) {
            throw new ResourceNotFoundException(EMPLOYEE_NOT_FOUND);
        }

        if(maintenanceRepository.existsByEmployee_IdEmployee(id)){
            throw new EntityInUseException(ENTITY_IN_USE);
        }

        employeeRepository.deleteById(id);
    }


    private void validateEmployeeEmail(String email) {
        if (employeeRepository.existsByEmail(email)){
            throw new BadRequestException(EMPLOYEE_EXIST_EMAIL);
        }
    }


    private Position getPositionById(Long id){
        return positionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(POSITION_NOT_FOUND));
    }

    public Bank getBankById(Long id){
        return bankRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(BANK_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public EmployeeResponseDTO getEmployeeById(Long id){
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee
                .map(this::mapToSpecificDTO)
                .orElseThrow(() -> new ResourceNotFoundException(EMPLOYEE_NOT_FOUND));
    }

    private Department getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(DEPARTMENT_NOT_FOUND));
    }

    private Category getCategoryById(Long id){
        return categoryRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(CATEGORY_NOT_FOUND));
    }


    private void associatePhones(Employee employee, Set<PhoneRequestDTO> phoneDTOS) {
        if (phoneDTOS != null) {
            // Eliminar teléfonos que ya no están en la nueva lista
            employee.getPhones().removeIf(existingPhone ->
                    phoneDTOS.stream().noneMatch(phoneDTO ->
                            existingPhone.getId_phone().getPhone().equals(phoneDTO.getPhone()) // Comparación correcta
                    )
            );

            // Agregar o actualizar teléfonos
            for (PhoneRequestDTO phoneDTO : phoneDTOS) {
                Phone phone = employee.getPhones().stream()
                        .filter(existingPhone -> existingPhone.getId_phone().getPhone().equals(phoneDTO.getPhone()))
                        .findFirst()
                        .orElse(new Phone(phoneDTO.getPhone(), employee));

                // Si el teléfono no está en la colección, lo agrega
                if (!employee.getPhones().contains(phone)) {
                    employee.getPhones().add(phone);
                }
            }
        }
    }

    private void associateAccounts(Employee employee, Set<AccountRequestDTO> accountDTOS) {
        if (accountDTOS != null) {
            // Validar y remover cuentas huérfanas
            employee.getAccounts().removeIf(existingAccount ->
                    accountDTOS.stream().noneMatch(accountDTO ->
                            existingAccount.getAccountNumber().equals(accountDTO.getAccountNumber()) &&
                                    existingAccount.getBank() != null &&
                                    existingAccount.getBank().getIdBank().equals(accountDTO.getBankId())
                    )
            );

            // Agregar o actualizar cuentas
            for (AccountRequestDTO accountDTO : accountDTOS) {
                if (accountDTO.getBankId() == null) {
                    throw new IllegalArgumentException("Bank ID cannot be null for account: " + accountDTO.getAccountNumber());
                }

                Bank bank = getBankById(accountDTO.getBankId());
                Account newAccount = new Account(accountDTO.getAccountNumber(), bank, employee);

                if (!employee.getAccounts().contains(newAccount)) {
                    employee.getAccounts().add(newAccount);
                }
            }
        }
    }



    private EmployeeResponseDTO mapToSpecificDTO(Employee employee) {
        if (employee instanceof PlantEmployee) {
            return employeeMapper.toPlantEmployeeResponseDTO((PlantEmployee) employee);
        } else if (employee instanceof ConstructionWorker) {
            return employeeMapper.toConstructionWorkerResponseDTO((ConstructionWorker) employee);
        } else {
            return employeeMapper.toResponseDTO(employee);
        }
    }

}
