package com.grupoingenios.sgpc.sgpc_api_final.repository.work;

import com.grupoingenios.sgpc.sgpc_api_final.entity.work.Work;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkRepository extends JpaRepository<Work, Long> {
    boolean existsByNameIgnoreCase(String name);
    boolean existsByWorkType_IdWorkType(Long idWorkType);
    boolean existsBySchedule_IdSchedule(Long idSchedule);
}
