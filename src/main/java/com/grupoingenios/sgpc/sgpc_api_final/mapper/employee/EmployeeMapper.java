package com.grupoingenios.sgpc.sgpc_api_final.mapper.employee;


import com.grupoingenios.sgpc.sgpc_api_final.dto.employee.*;
import com.grupoingenios.sgpc.sgpc_api_final.entity.employee.ConstructionWorker;
import com.grupoingenios.sgpc.sgpc_api_final.entity.employee.Employee;
import com.grupoingenios.sgpc.sgpc_api_final.entity.employee.Phone;
import com.grupoingenios.sgpc.sgpc_api_final.entity.employee.PlantEmployee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Mapper para convertir entre las entidades `Employee`, `PlantEmployee`, `ConstructionWorker` y sus respectivos DTOs.
 * Utiliza MapStruct para la conversión de datos entre las entidades y sus representaciones DTO.
 */
@Mapper(componentModel = "spring", uses = {AccountMapper.class})
public interface EmployeeMapper {


    /**
     * Mapea un objeto `EmployeeRequestDTO` a una entidad `Employee`.
     *
     * @param dto El objeto DTO `EmployeeRequestDTO` que se desea mapear.
     * @return La entidad `Employee` con los datos del DTO.
     */
    @Mapping(source = "dto.positionId", target = "position.idPosition")
    @Mapping(source = "dto.categoryId", target = "category.idCategory")
    @Mapping(source = "dto.name", target = "name")
    @Mapping(target="idEmployee", ignore=true)
    @Mapping(target = "phones", ignore = true) // Evitar conflictos iniciales
    @Mapping(target = "accounts", source = "accounts") // Agrega esta línea para mapear accounts
    Employee toEntity(EmployeeRequestDTO dto);

    /**
     * Mapea una entidad `Employee` a un objeto DTO `EmployeeResponseDTO`.
     *
     * @param employee La entidad `Employee` que se desea mapear.
     * @return Un objeto `EmployeeResponseDTO` con los datos de la entidad `Employee`.
     */
    @Mapping(source = "employee.position.name", target = "positionName")
    @Mapping(source = "employee.category.name", target = "categoryName")
    @Mapping(target = "phones", qualifiedByName = "mapPhonesToDTOs")
    @Mapping(target = "employeeType", expression = "java(employee.getEmployeeType())")
    @Mapping(source = "category.idCategory", target = "categoryId")
    @Mapping(source = "position.idPosition", target = "positionId")
    EmployeeResponseDTO toResponseDTO(Employee employee);


    /**
     * Mapea un objeto `PlantEmployeeRequestDTO` a una entidad `PlantEmployee`.
     *
     * @param plantEmployeeRequestDTO El objeto DTO `PlantEmployeeRequestDTO` que se desea mapear.
     * @return La entidad `PlantEmployee` con los datos del DTO.
     */
    // Mapeo específico para PlantEmployeeRequestDTO a PlantEmployee
    @Mapping(source = "positionId", target = "position.idPosition")
    @Mapping(source = "categoryId", target = "category.idCategory")
    @Mapping(target = "phones", ignore = true) // Ignora phones en PlantEmployee
    @Mapping(source = "workingHours", target = "workingHours")
    @Mapping(source = "salary", target = "salary")
    PlantEmployee toPlantEmployeeEntity(PlantEmployeeRequestDTO plantEmployeeRequestDTO);


    /**
     * Mapea un objeto `ConstructionWorkerRequestDTO` a una entidad `ConstructionWorker`.
     *
     * @param constructionWorkerRequestDTO El objeto DTO `ConstructionWorkerRequestDTO` que se desea mapear.
     * @return La entidad `ConstructionWorker` con los datos del DTO.
     */
    @Mapping(source = "positionId", target = "position.idPosition")
    @Mapping(source = "categoryId", target = "category.idCategory")
    @Mapping(target = "phones", ignore = true) // Ignora phones en PlantEmployee
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    ConstructionWorker toConstructionWorkerEntity(ConstructionWorkerRequestDTO constructionWorkerRequestDTO);



    /**
     * Mapea un conjunto de objetos `Phone` a un conjunto de objetos `PhoneResponseDTO`.
     * Este método se utiliza para convertir la relación de teléfonos de un empleado en un DTO.
     *
     * @param phones El conjunto de objetos `Phone` que se desea mapear.
     * @return Un conjunto de objetos `PhoneResponseDTO` con los datos de los teléfonos del empleado.
     */
    // Método para convertir de entidad a DTO (Phone -> PhoneDTO)
    @Named("mapPhonesToDTOs")
    default Set<PhoneResponseDTO> mapPhonesToDTOs(Set<Phone> phones) {
        return phones.stream()
                .map(phone -> {
                    PhoneResponseDTO dto = new PhoneResponseDTO();
                    dto.setPhone(phone.getId_phone().getPhone());
                    dto.setEmployeeId(phone.getEmployee().getIdEmployee());
                    return dto;
                })
                .collect(Collectors.toSet());
    }

    // Método específico para mapear PlantEmployee a PlantEmployeeResponseDTO
    @Mapping(source = "workingHours", target = "workingHours")
    @Mapping(source = "salary", target = "salary")
    @Mapping(source = "department.name", target = "departmentName")
    @Mapping(target = "phones", qualifiedByName = "mapPhonesToDTOs")
    @Mapping(source = "category.idCategory", target = "categoryId")
    @Mapping(source = "position.idPosition", target = "positionId")
    @Mapping(source = "department.idDepartment", target = "departmentId")
    @Mapping(source = "position.name", target = "positionName")
    @Mapping(source = "category.name", target = "categoryName")
    PlantEmployeeResponseDTO toPlantEmployeeResponseDTO(PlantEmployee plantEmployee);

    // Método específico para mapear ConstructionWorker a ConstructionWorkerResponseDTO
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    @Mapping(target = "phones", qualifiedByName = "mapPhonesToDTOs")
    @Mapping(source = "category.idCategory", target = "categoryId")
    @Mapping(source = "position.idPosition", target = "positionId")
    @Mapping(source = "position.name", target = "positionName")
    @Mapping(source = "category.name", target = "categoryName")
    ConstructionWorkerResponseDTO toConstructionWorkerResponseDTO(ConstructionWorker constructionWorker);


}