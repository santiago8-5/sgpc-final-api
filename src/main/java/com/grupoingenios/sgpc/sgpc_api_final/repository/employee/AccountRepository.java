package com.grupoingenios.sgpc.sgpc_api_final.repository.employee;

import com.grupoingenios.sgpc.sgpc_api_final.entity.employee.Account;
import com.grupoingenios.sgpc.sgpc_api_final.entity.employee.AccountId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, AccountId> {

    boolean existsByBank_IdBank(Long bankId);

}
