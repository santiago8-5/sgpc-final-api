package com.grupoingenios.sgpc.sgpc_api_final.mapper.employee;

import com.grupoingenios.sgpc.sgpc_api_final.dto.employee.AccountResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.employee.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(target = "bankId", expression = "java(account.getBank() != null ? account.getBank().getIdBank() : null)")
    @Mapping(target = "nameBank", expression = "java(account.getBank() != null ? account.getBank().getName() : null)")
    AccountResponseDTO toAccountDTO(Account account);

    default Set<AccountResponseDTO> toAccountDTOs(Set<Account> accounts) {
        return accounts.stream()
                .map(this::toAccountDTO)
                .collect(Collectors.toSet());
    }

}
