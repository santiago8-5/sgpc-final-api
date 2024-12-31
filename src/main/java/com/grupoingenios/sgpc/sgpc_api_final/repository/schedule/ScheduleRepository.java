package com.grupoingenios.sgpc.sgpc_api_final.repository.schedule;

import com.grupoingenios.sgpc.sgpc_api_final.entity.schedule.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    boolean existsByNameIgnoreCase(String name);
}