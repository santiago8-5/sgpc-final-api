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

    @Transactional(readOnly = true)
    public List<DepartmentResponseDTO> getAllDepartment(){
        return departmentRepository
                .findAll()
                .stream()
                .map(departmentMapper::toResponseDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public DepartmentResponseDTO getDepartmentById(Long id){
        Department department = departmentRepository
                .findById(id).orElseThrow(()-> new ResourceNotFoundException(DEPARTMENT_NOT_FOUND));
        return departmentMapper.toResponseDto(department);
    }


    @Transactional
    public DepartmentResponseDTO createDepartment(DepartmentRequestDTO departmentRequestDTO){
        if(departmentRepository.existsByNameIgnoreCase(departmentRequestDTO.getName())){
            throw new BadRequestException(DEPARTMENT_EXIST_NAME);
        }

        Department department = departmentMapper.toEntity(departmentRequestDTO);
        Department departmentCreated = departmentRepository.save(department);

        return departmentMapper.toResponseDto(departmentCreated);

    }

    @Transactional
    public DepartmentResponseDTO updateDepartment(Long id, DepartmentRequestDTO departmentRequestDTO){
        Department existingDepartment = departmentRepository.findById(id).
                orElseThrow(()-> new ResourceNotFoundException(DEPARTMENT_NOT_FOUND));

        validateUniqueName(existingDepartment.getName(), departmentRequestDTO.getName());

        departmentMapper.updatedDepartmentFromDTO(departmentRequestDTO, existingDepartment);

        Department departmentUpdated = departmentRepository.save(existingDepartment);

        return departmentMapper.toResponseDto(departmentUpdated);
    }

    @Transactional
    public void deleteDepartment(Long id){

        if(!departmentRepository.existsById(id)){
            throw new ResourceNotFoundException(DEPARTMENT_NOT_FOUND);
        }

        // Verificar si hay empleados asociados a categoría
        if(plantEmployeeRepository.existsByDepartment_IdDepartment(id)){
            throw new EntityInUseException(ENTITY_IN_USE);
        }

        departmentRepository.deleteById(id);
    }


    private void validateUniqueName(String currentName, String newName){
        if(!currentName.equalsIgnoreCase(newName) && departmentRepository.existsByNameIgnoreCase(newName)){
            throw new BadRequestException(DEPARTMENT_EXIST_NAME);
        }
    }


}
