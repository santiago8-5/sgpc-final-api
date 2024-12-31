package com.grupoingenios.sgpc.sgpc_api_final.repository.user;

import com.grupoingenios.sgpc.sgpc_api_final.entity.user.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Long> {

    boolean existsByNameIgnoreCase(String name);

    Optional<Rol> findByName(String name);


}
