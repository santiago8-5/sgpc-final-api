package com.grupoingenios.sgpc.sgpc_api_final.repository.work;

import com.grupoingenios.sgpc.sgpc_api_final.dto.work.ClientWorkResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.work.ClientWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClientWorkRepository extends JpaRepository<ClientWork, Long> {
    @Query("SELECT COUNT(cw) > 0 FROM ClientWork cw WHERE cw.client.idClient = :clientId AND cw.work.idWork = :workId")
    boolean existsByClient_IdAndWork_Id(Long clientId, Long workId);


    // comprobar si hay un cliente asignado
    boolean existsByClient_IdClient(Long id);

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
    """)
    List<ClientWorkResponseDTO> findAllClientWorkDTOs();


}
