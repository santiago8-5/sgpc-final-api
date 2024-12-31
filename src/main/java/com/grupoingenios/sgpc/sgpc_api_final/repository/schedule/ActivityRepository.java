package com.grupoingenios.sgpc.sgpc_api_final.repository.schedule;
import com.grupoingenios.sgpc.sgpc_api_final.entity.schedule.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    boolean existsByNameIgnoreCase(String name);
    boolean existsByStage_IdStage(Long idStage);

}
