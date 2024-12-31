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

    @Transactional(readOnly = true)
    public List<ClientResponseDTO> getAllClients(){
        return clientRepository
                .findAll()
                .stream()
                .map(clientMapper::toResponseDTO)
                .toList();
    }

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

    @Transactional
    public ClientResponseDTO updateClient(Long id, ClientRequestDTO clientRequestDTO){

        // Recuperando el cliente existente
        Client existingClient = clientRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(CLIENT_NOT_FOUND));

        // Validando único rfc
        validateUniqueRfc(existingClient.getRfc(), clientRequestDTO.getRfc());

        // Validando único email
        validateUniqueEmail(existingClient.getEmail(), clientRequestDTO.getEmail());

        // Actualizar los campos
        clientMapper.updatedClientFromDTO(clientRequestDTO, existingClient);

        // Guardamos los da datos modificados
        Client updatedClient = clientRepository.save(existingClient);

        // Devolvemos la respuesta
        return clientMapper.toResponseDTO(updatedClient);
    }


    @Transactional(readOnly = true)
    public ClientResponseDTO getClientById(Long id){
        Optional<Client> client = clientRepository.findById(id);
        return client.map(clientMapper::toResponseDTO)
                .orElseThrow(()-> new ResourceNotFoundException(CLIENT_NOT_FOUND));
    }

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


    private void validateUniqueRfc(String currentRfc, String newRfc){
        if(!currentRfc.equalsIgnoreCase(newRfc) && clientRepository.existsByRfcIgnoreCase(newRfc)){
            throw new BadRequestException(CLIENT_EXIST_RFC);
        }
    }

    private void validateUniqueEmail(String currentEmail, String newEmail){
        if(!currentEmail.equalsIgnoreCase(newEmail) && clientRepository.existsByRfcIgnoreCase(newEmail)){
            throw new BadRequestException(CLIENT_EXIST_EMAIL);
        }
    }



}
