package com.grupoingenios.sgpc.sgpc_api_final.repository.work;

import com.grupoingenios.sgpc.sgpc_api_final.dto.work.ClientWorkResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.work.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Repositorio para la entidad `Work`.
 * Proporciona métodos para realizar operaciones sobre las obras y sus relaciones con los clientes.
 */
public interface WorkRepository extends JpaRepository<Work, Long> {

    /**
     * Verifica si existe una obra con el nombre proporcionado (ignorando mayúsculas/minúsculas).
     *
     * @param name El nombre de la obra.
     * @return `true` si existe una obra con el nombre especificado, de lo contrario `false`.
     */
    boolean existsByNameIgnoreCase(String name);

    /**
     * Verifica si existe una obra con un tipo de trabajo específico.
     *
     * @param idWorkType El identificador del tipo de obra.
     * @return `true` si existe una obra con el tipo de trabajo indicado, de lo contrario `false`.
     */
    boolean existsByWorkType_IdWorkType(Long idWorkType);

    /**
     * Verifica si existe una obra con un cronograma asignado.
     *
     * @param idSchedule El identificador del cronograma.
     * @return `true` si existe una obra asignada al cronograma, de lo contrario `false`.
     */
    boolean existsBySchedule_IdSchedule(Long idSchedule);

    /**
     * Verifica si existe una obra con un ID específico que ya tenga un cronograma asignado.
     *
     * @param idWork El identificador de la obra.
     * @return `true` si la obra tiene un cronograma asignado, de lo contrario `false`.
     */
    boolean existsByIdWorkAndScheduleIsNotNull(Long idWork);

    /**
     * Recupera todos los clientes relacionados con una obra específica.
     *
     * @param workId El identificador de la obra.
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
    WHERE w.idWork = :workId
    """)
    List<ClientWorkResponseDTO> findAllClientWorkDTOsByWorkId(@Param("workId") Long workId);

}
