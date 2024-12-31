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

@Mapper(componentModel = "spring", uses = {AccountMapper.class})
public interface EmployeeMapper {

    @Mapping(source = "dto.positionId", target = "position.idPosition")
    @Mapping(source = "dto.categoryId", target = "category.idCategory")
    @Mapping(source = "dto.name", target = "name")
    @Mapping(target="idEmployee", ignore=true)
    @Mapping(target = "phones", ignore = true) // Evitar conflictos iniciales
    @Mapping(target = "accounts", source = "accounts") // Agrega esta línea para mapear accounts
    Employee toEntity(EmployeeRequestDTO dto);

    @Mapping(source = "employee.position.name", target = "positionName")
    @Mapping(source = "employee.category.name", target = "categoryName")
    @Mapping(target = "phones", qualifiedByName = "mapPhonesToDTOs")
    @Mapping(target = "employeeType", expression = "java(employee.getEmployeeType())")
    EmployeeResponseDTO toResponseDTO(Employee employee);


    // Mapeo específico para PlantEmployeeRequestDTO a PlantEmployee
    @Mapping(source = "positionId", target = "position.idPosition")
    @Mapping(source = "categoryId", target = "category.idCategory")
    @Mapping(target = "phones", ignore = true) // Ignora phones en PlantEmployee
    @Mapping(source = "workingHours", target = "workingHours")
    @Mapping(source = "salary", target = "salary")
    PlantEmployee toPlantEmployeeEntity(PlantEmployeeRequestDTO plantEmployeeRequestDTO);



    @Mapping(source = "positionId", target = "position.idPosition")
    @Mapping(source = "categoryId", target = "category.idCategory")
    @Mapping(target = "phones", ignore = true) // Ignora phones en PlantEmployee
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    ConstructionWorker toConstructionWorkerEntity(ConstructionWorkerRequestDTO constructionWorkerRequestDTO);



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


}