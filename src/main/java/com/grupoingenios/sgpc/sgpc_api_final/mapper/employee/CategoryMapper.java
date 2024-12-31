package com.grupoingenios.sgpc.sgpc_api_final.mapper.employee;


import com.grupoingenios.sgpc.sgpc_api_final.dto.employee.CategoryRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.employee.CategoryResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.employee.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toEntity(CategoryRequestDTO categoryDTO);

    CategoryResponseDTO toResponseDTO(Category category);

    @Mapping(target = "idCategory", ignore = true)
    void updatedCategoryFromDTO(CategoryRequestDTO dto, @MappingTarget Category entity);

}