package com.grupoingenios.sgpc.sgpc_api_final.repository.machinery;

import com.grupoingenios.sgpc.sgpc_api_final.entity.machinery.Machinery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MachineryRepository extends JpaRepository<Machinery, Long> {
    boolean existsByNameIgnoreCase(String name);
}
