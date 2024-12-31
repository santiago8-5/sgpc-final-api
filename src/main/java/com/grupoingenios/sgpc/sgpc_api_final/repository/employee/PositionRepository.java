package com.grupoingenios.sgpc.sgpc_api_final.repository.employee;

import com.grupoingenios.sgpc.sgpc_api_final.entity.employee.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position, Long> {
    boolean existsByNameIgnoreCase(String name);
}