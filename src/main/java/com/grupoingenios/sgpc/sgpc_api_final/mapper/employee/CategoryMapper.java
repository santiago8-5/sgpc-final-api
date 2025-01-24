package com.grupoingenios.sgpc.sgpc_api_final.mapper.employee;


import com.grupoingenios.sgpc.sgpc_api_final.dto.employee.CategoryRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.employee.CategoryResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.employee.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para convertir entre la entidad `Category` y los DTOs `CategoryRequestDTO` y `CategoryResponseDTO`.
 * Utiliza MapStruct para la conversión de datos entre las entidades y sus representaciones DTO.
 */
@Mapper(componentModel = "spring")
public interface CategoryMapper {

    /**
     * Mapea un objeto DTO `CategoryRequestDTO` a una entidad `Category`.
     *
     * @param categoryDTO El objeto DTO `CategoryRequestDTO` que se desea mapear.
     * @return La entidad `Category` con los datos del DTO.
     */
    Category toEntity(CategoryRequestDTO categoryDTO);

    /**
     * Mapea una entidad `Category` a un objeto DTO `CategoryResponseDTO`.
     *
     * @param category La entidad `Category` que se desea mapear.
     * @return Un objeto `CategoryResponseDTO` con los datos de la entidad `Category`.
     */
    CategoryResponseDTO toResponseDTO(Category category);


    /**
     * Actualiza una entidad `Category` existente con los valores de un objeto DTO `CategoryRequestDTO`.
     * El campo `idCategory` es ignorado durante la actualización.
     *
     * @param dto El objeto DTO `CategoryRequestDTO` con los nuevos datos.
     * @param entity La entidad `Category` que se desea actualizar.
     */
    @Mapping(target = "idCategory", ignore = true)
    void updatedCategoryFromDTO(CategoryRequestDTO dto, @MappingTarget Category entity);

}