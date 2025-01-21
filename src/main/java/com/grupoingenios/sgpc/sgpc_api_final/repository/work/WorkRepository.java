package com.grupoingenios.sgpc.sgpc_api_final.repository.work;

import com.grupoingenios.sgpc.sgpc_api_final.dto.work.ClientWorkResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.work.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorkRepository extends JpaRepository<Work, Long> {
    boolean existsByNameIgnoreCase(String name);
    boolean existsByWorkType_IdWorkType(Long idWorkType);
    boolean existsBySchedule_IdSchedule(Long idSchedule);
    boolean existsByIdWorkAndScheduleIsNotNull(Long idWork);

    @Query("""
    SELECT NEW com.grupoingenios.sgpc.sgpc_api_final.dto.work.ClientWorkResponseDTO(
        cw.id,
        c.name,
        c.idClient,
        w.name,
        cw.role,
        cw.assignedAt,
        cw.status,
        cw.createdAt,
        cw.lastModifiedAt
    )
    FROM ClientWork cw
    JOIN cw.client c
    JOIN cw.work w
    WHERE w.idWork = :workId
    """)
    List<ClientWorkResponseDTO> findAllClientWorkDTOsByWorkId(@Param("workId") Long workId);

}
