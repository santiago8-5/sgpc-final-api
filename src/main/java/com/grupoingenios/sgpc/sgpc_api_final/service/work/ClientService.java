package com.grupoingenios.sgpc.sgpc_api_final.service.work;

import com.grupoingenios.sgpc.sgpc_api_final.dto.work.ClientRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.work.ClientResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.work.Client;
import com.grupoingenios.sgpc.sgpc_api_final.exception.BadRequestException;
import com.grupoingenios.sgpc.sgpc_api_final.exception.EntityInUseException;
import com.grupoingenios.sgpc.sgpc_api_final.exception.ResourceNotFoundException;
import com.grupoingenios.sgpc.sgpc_api_final.mapper.work.ClientMapper;
import com.grupoingenios.sgpc.sgpc_api_final.repository.work.ClientRepository;
import com.grupoingenios.sgpc.sgpc_api_final.repository.work.ClientWorkRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

import static com.grupoingenios.sgpc.sgpc_api_final.constants.AppConstant.*;


/**
 * Servicio encargado de gestionar las operaciones relacionadas con los clientes.
 * Proporciona métodos para realizar operaciones CRUD sobre los clientes,
 * además de validar reglas de negocio como la unicidad del RFC y el email de los clientes.
 */
@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final ClientWorkRepository clientWorkRepository;

    public ClientService(ClientRepository clientRepository, ClientMapper clientMapper, ClientWorkRepository clientWorkRepository ){
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
        this.clientWorkRepository = clientWorkRepository;
    }


    /**
     * Obtiene todos los clientes en el sistema.
     *
     * @return Lista de clientes como DTOs.
     */
    @Transactional(readOnly = true)
    public List<ClientResponseDTO> getAllClients(){
        return clientRepository
                .findAll()
                .stream()
                .map(clientMapper::toResponseDTO)
                .toList();
    }


    /**
     * Crea un nuevo cliente en el sistema.
     *
     * @param clientRequestDTO DTO con los datos del cliente a crear.
     * @return El cliente creado como DTO.
     * @throws BadRequestException Si el email o el RFC del cliente ya están en uso.
     */
    @Transactional
    public ClientResponseDTO createClient(ClientRequestDTO clientRequestDTO){
        if (clientRepository.existsByEmail(clientRequestDTO.getEmail())) {
            throw new BadRequestException(CLIENT_EXIST_EMAIL);
        }

        if (clientRepository.existsByRfcIgnoreCase(clientRequestDTO.getRfc())) {
            throw new BadRequestException(CLIENT_EXIST_RFC);
        }

        Client client = clientMapper.toEntity(clientRequestDTO);

        Client savedClient = clientRepository.save(client);

        return clientMapper.toResponseDTO(savedClient);
    }


    /**
     * Actualiza un cliente existente en el sistema.
     *
     * @param id El ID del cliente a actualizar.
     * @param clientRequestDTO DTO con los nuevos datos del cliente.
     * @return El cliente actualizado como DTO.
     * @throws ResourceNotFoundException Si el cliente no existe.
     * @throws BadRequestException Si el nuevo email o RFC ya están en uso.
     */
    @Transactional
    public ClientResponseDTO updateClient(Long id, ClientRequestDTO clientRequestDTO){

        Client existingClient = clientRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(CLIENT_NOT_FOUND));

        validateUniqueRfc(existingClient.getRfc(), clientRequestDTO.getRfc());

        validateUniqueEmail(existingClient.getEmail(), clientRequestDTO.getEmail());

        clientMapper.updatedClientFromDTO(clientRequestDTO, existingClient);

        Client updatedClient = clientRepository.save(existingClient);

        return clientMapper.toResponseDTO(updatedClient);
    }


    /**
     * Obtiene los detalles de un cliente por su ID.
     *
     * @param id El ID del cliente a buscar.
     * @return El cliente correspondiente como DTO.
     * @throws ResourceNotFoundException Si el cliente no existe.
     */
    @Transactional(readOnly = true)
    public ClientResponseDTO getClientById(Long id){
        Optional<Client> client = clientRepository.findById(id);
        return client.map(clientMapper::toResponseDTO)
                .orElseThrow(()-> new ResourceNotFoundException(CLIENT_NOT_FOUND));
    }


    /**
     * Elimina un cliente del sistema.
     *
     * @param id El ID del cliente a eliminar.
     * @throws ResourceNotFoundException Si el cliente no existe.
     * @throws EntityInUseException Si el cliente tiene trabajos asociados.
     */
    @Transactional
    public void deleteClient(Long id){
        if(!clientRepository.existsById(id)){
            throw new ResourceNotFoundException(CLIENT_NOT_FOUND);
        }

        if(clientWorkRepository.existsByClient_IdClient(id)){
            throw new EntityInUseException(ENTITY_IN_USE);
        }

        clientRepository.deleteById(id);

    }

    /**
     * Valida que el RFC no esté en uso.
     *
     * @param currentRfc El RFC actual del cliente.
     * @param newRfc El nuevo RFC del cliente.
     * @throws BadRequestException Si el nuevo RFC ya está en uso.
     */
    private void validateUniqueRfc(String currentRfc, String newRfc){
        if(!currentRfc.equalsIgnoreCase(newRfc) && clientRepository.existsByRfcIgnoreCase(newRfc)){
            throw new BadRequestException(CLIENT_EXIST_RFC);
        }
    }

    /**
     * Valida que el email no esté en uso.
     *
     * @param currentEmail El email actual del cliente.
     * @param newEmail El nuevo email del cliente.
     * @throws BadRequestException Si el nuevo email ya está en uso.
     */
    private void validateUniqueEmail(String currentEmail, String newEmail){
        if(!currentEmail.equalsIgnoreCase(newEmail) && clientRepository.existsByRfcIgnoreCase(newEmail)){
            throw new BadRequestException(CLIENT_EXIST_EMAIL);
        }
    }

}
