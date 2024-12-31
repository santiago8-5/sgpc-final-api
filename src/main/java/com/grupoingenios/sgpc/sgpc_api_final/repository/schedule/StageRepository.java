package com.grupoingenios.sgpc.sgpc_api_final.repository.schedule;

import com.grupoingenios.sgpc.sgpc_api_final.entity.schedule.Stage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StageRepository extends JpaRepository<Stage, Long> {

    boolean existsByNameIgnoreCase(String name);

}
