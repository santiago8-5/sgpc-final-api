package com.grupoingenios.sgpc.sgpc_api_final.repository.work;

import com.grupoingenios.sgpc.sgpc_api_final.dto.work.ClientWorkResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.work.ClientWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

/**
 * Repositorio para la entidad `ClientWork`.
 * Proporciona métodos para realizar operaciones sobre las relaciones entre clientes y obras.
 */
public interface ClientWorkRepository extends JpaRepository<ClientWork, Long> {

    /**
     * Verifica si existe una relación entre un cliente y una obra específica.
     *
     * @param clientId El identificador del cliente.
     * @param workId El identificador de la obra.
     * @return `true` si existe la relación entre el cliente y la obra, de lo contrario `false`.
     */
    @Query("SELECT COUNT(cw) > 0 FROM ClientWork cw WHERE cw.client.idClient = :clientId AND cw.work.idWork = :workId")
    boolean existsByClient_IdAndWork_Id(Long clientId, Long workId);


    /**
     * Verifica si existe algún cliente asignado en la relación `ClientWork`.
     *
     * @param id El identificador del cliente.
     * @return `true` si el cliente está asignado a alguna obra, de lo contrario `false`.
     */
    boolean existsByClient_IdClient(Long id);

    /**
     * Recupera todos los `ClientWork` y devuelve una lista de objetos `ClientWorkResponseDTO`.
     *
     * @return Lista de objetos `ClientWorkResponseDTO` que representan las relaciones cliente-obra.
     */
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
