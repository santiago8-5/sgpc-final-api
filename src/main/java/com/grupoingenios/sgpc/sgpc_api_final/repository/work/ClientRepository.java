package com.grupoingenios.sgpc.sgpc_api_final.repository.work;

import com.grupoingenios.sgpc.sgpc_api_final.entity.work.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

    boolean existsByEmail(String email);
    boolean existsByRfcIgnoreCase(String name);

}

