package com.grupoingenios.sgpc.sgpc_api_final.mapper.user;

import com.grupoingenios.sgpc.sgpc_api_final.dto.user.RolRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.user.RolResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.user.Rol;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RolMapper {

    Rol toEntity(RolRequestDTO rolRequestDTO);

    RolResponseDTO toResponseDto(Rol rol);

    @Mapping(target = "id_rol", ignore = true)
    void updatedRolFromDTO(RolRequestDTO dto, @MappingTarget Rol entity);

}
