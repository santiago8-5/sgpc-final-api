package com.grupoingenios.sgpc.sgpc_api_final.service.work;


import com.grupoingenios.sgpc.sgpc_api_final.dto.work.ClientWorkResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.repository.work.ClientWorkRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Servicio encargado de gestionar las operaciones relacionadas con las asignaciones de trabajos a clientes.
 * Proporciona métodos para recuperar y gestionar asignaciones entre clientes y trabajos.
 */
@Service
public class ClientWorkService {

    private final ClientWorkRepository clientWorkRepository;


    /**
     * Constructor que inicializa el servicio con su repositorio de asignaciones de trabajos a clientes.
     *
     * @param clientWorkRepository Repositorio para gestionar las asignaciones de trabajos a clientes.
     */
    public ClientWorkService(ClientWorkRepository clientWorkRepository) {
        this.clientWorkRepository = clientWorkRepository;

    }


    /**
     * Obtiene todas las asignaciones de trabajos a clientes.
     *
     * @return Lista de asignaciones de trabajos a clientes como DTOs.
     */
    @Transactional(readOnly = true)
    public List<ClientWorkResponseDTO> getAllClientWork(){
        return clientWorkRepository.findAllClientWorkDTOs();
    }


}
