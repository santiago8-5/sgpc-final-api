package com.grupoingenios.sgpc.sgpc_api_final.repository.work;

import com.grupoingenios.sgpc.sgpc_api_final.entity.work.WorkType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkTypeRepository extends JpaRepository<WorkType, Long> {
    boolean existsWorkTypeByNameIgnoreCase(String name);
}