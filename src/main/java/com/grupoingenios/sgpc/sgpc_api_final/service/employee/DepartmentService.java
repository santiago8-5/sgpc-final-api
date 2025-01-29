package com.grupoingenios.sgpc.sgpc_api_final.service.employee;

import com.grupoingenios.sgpc.sgpc_api_final.dto.employee.DepartmentRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.employee.DepartmentResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.employee.Department;
import com.grupoingenios.sgpc.sgpc_api_final.exception.BadRequestException;
import com.grupoingenios.sgpc.sgpc_api_final.exception.EntityInUseException;
import com.grupoingenios.sgpc.sgpc_api_final.exception.ResourceNotFoundException;
import com.grupoingenios.sgpc.sgpc_api_final.mapper.employee.DepartmentMapper;
import com.grupoingenios.sgpc.sgpc_api_final.repository.employee.DepartmentRepository;
import com.grupoingenios.sgpc.sgpc_api_final.repository.employee.PlantEmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.grupoingenios.sgpc.sgpc_api_final.constants.AppConstant.*;


/**
 * Servicio encargado de gestionar las operaciones relacionadas con los departamentos.
 * Proporciona métodos para realizar operaciones CRUD sobre los departamentos,
 * además de validar reglas de negocio como la unicidad del nombre del departamento.
 */
@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;
    private final PlantEmployeeRepository plantEmployeeRepository;

    public DepartmentService(DepartmentRepository departmentRepository, DepartmentMapper departmentMapper, PlantEmployeeRepository plantEmployeeRepository) {
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
        this.plantEmployeeRepository = plantEmployeeRepository;
    }

    /**
     * Obtiene todas las categorías de empleados en el sistema.
     *
     * @return Lista de departamentos como DTOs.
     */
    @Transactional(readOnly = true)
    public List<DepartmentResponseDTO> getAllDepartment(){
        return departmentRepository
                .findAll()
                .stream()
                .map(departmentMapper::toResponseDto)
                .toList();
    }


    /**
     * Obtiene los detalles de un departamento por su ID.
     *
     * @param id El ID del departamento a buscar.
     * @return El departamento correspondiente como DTO.
     */
    @Transactional(readOnly = true)
    public DepartmentResponseDTO getDepartmentById(Long id){
        Department department = departmentRepository
                .findById(id).orElseThrow(()-> new ResourceNotFoundException(DEPARTMENT_NOT_FOUND));
        return departmentMapper.toResponseDto(department);
    }

    /**
     * Crea un nuevo departamento en el sistema.
     *
     * @param departmentRequestDTO DTO con los datos del departamento a crear.
     * @return El departamento creado como DTO.
     * @throws BadRequestException Si el nombre del departamento ya está en uso.
     */
    @Transactional
    public DepartmentResponseDTO createDepartment(DepartmentRequestDTO departmentRequestDTO){
        if(departmentRepository.existsByNameIgnoreCase(departmentRequestDTO.getName())){
            throw new BadRequestException(DEPARTMENT_EXIST_NAME);
        }

        Department department = departmentMapper.toEntity(departmentRequestDTO);
        Department departmentCreated = departmentRepository.save(department);

        return departmentMapper.toResponseDto(departmentCreated);

    }


    /**
     * Actualiza un departamento existente en el sistema.
     *
     * @param id El ID del departamento a actualizar.
     * @param departmentRequestDTO DTO con los nuevos datos del departamento.
     * @return El departamento actualizado como DTO.
     * @throws ResourceNotFoundException Si el departamento no existe.
     * @throws BadRequestException Si el nuevo nombre ya está en uso.
     */
    @Transactional
    public DepartmentResponseDTO updateDepartment(Long id, DepartmentRequestDTO departmentRequestDTO){
        Department existingDepartment = departmentRepository.findById(id).
                orElseThrow(()-> new ResourceNotFoundException(DEPARTMENT_NOT_FOUND));

        validateUniqueName(existingDepartment.getName(), departmentRequestDTO.getName());

        departmentMapper.updatedDepartmentFromDTO(departmentRequestDTO, existingDepartment);

        Department departmentUpdated = departmentRepository.save(existingDepartment);

        return departmentMapper.toResponseDto(departmentUpdated);
    }


    /**
     * Elimina un departamento del sistema.
     *
     * @param id El ID del departamento a eliminar.
     * @throws ResourceNotFoundException Si el departamento no existe.
     * @throws EntityInUseException Si el departamento tiene empleados asociados.
     */
    @Transactional
    public void deleteDepartment(Long id){

        if(!departmentRepository.existsById(id)){
            throw new ResourceNotFoundException(DEPARTMENT_NOT_FOUND);
        }
        if(plantEmployeeRepository.existsByDepartment_IdDepartment(id)){
            throw new EntityInUseException(ENTITY_IN_USE);
        }
        departmentRepository.deleteById(id);
    }


    /**
     * Valida que el nuevo nombre de departamento no esté en uso.
     *
     * @param currentName El nombre actual del departamento.
     * @param newName El nuevo nombre del departamento.
     * @throws BadRequestException Si el nuevo nombre ya está en uso.
     */
    private void validateUniqueName(String currentName, String newName){
        if(!currentName.equalsIgnoreCase(newName) && departmentRepository.existsByNameIgnoreCase(newName)){
            throw new BadRequestException(DEPARTMENT_EXIST_NAME);
        }
    }


}
