package com.grupoingenios.sgpc.sgpc_api_final.mapper.employee;

import com.grupoingenios.sgpc.sgpc_api_final.dto.employee.AccountResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.employee.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Mapper para convertir entre la entidad `Account` y el DTO `AccountResponseDTO`.
 * Utiliza MapStruct para la conversión de datos entre las entidades y sus representaciones DTO.
 */
@Mapper(componentModel = "spring")
public interface AccountMapper {

    /**
     * Mapea una entidad `Account` a un objeto DTO `AccountResponseDTO`.
     * Se realizan transformaciones para obtener el ID del banco y el nombre del banco desde la relación `bank`.
     *
     * @param account La entidad `Account` que se desea mapear.
     * @return Un objeto `AccountResponseDTO` con los datos de la entidad `Account`.
     */
    @Mapping(target = "bankId", expression = "java(account.getBank() != null ? account.getBank().getIdBank() : null)")
    @Mapping(target = "nameBank", expression = "java(account.getBank() != null ? account.getBank().getName() : null)")
    AccountResponseDTO toAccountDTO(Account account);

    /**
     * Mapea un conjunto de entidades `Account` a un conjunto de objetos DTO `AccountResponseDTO`.
     *
     * @param accounts El conjunto de entidades `Account` a mapear.
     * @return Un conjunto de objetos `AccountResponseDTO`.
     */
    default Set<AccountResponseDTO> toAccountDTOs(Set<Account> accounts) {
        return accounts.stream()
                .map(this::toAccountDTO)
                .collect(Collectors.toSet());
    }

}
