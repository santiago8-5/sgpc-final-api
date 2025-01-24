package com.grupoingenios.sgpc.sgpc_api_final.controller.work;

import com.grupoingenios.sgpc.sgpc_api_final.dto.work.ClientRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.work.ClientResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.service.work.ClientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controlador REST para gestionar operaciones relacionadas con clientes.
 * Proporciona endpoints para realizar operaciones CRUD sobre los clientes.
 */
@RestController
@RequestMapping("/api/v1/clients")
@Validated
public class ClientController {

    private final ClientService clientService;

    /**
     * Constructor para inyectar el servicio de clientes.
     *
     * @param clientService Servicio que contiene la lógica de negocio para los clientes.
     */
    public ClientController(ClientService clientService){
        this.clientService = clientService;
    }

    /**
     * Obtiene la lista de todos los clientes.
     *
     * @return Respuesta con la lista de clientes y un estado HTTP 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<ClientResponseDTO>> getAllClients(){
        List<ClientResponseDTO> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }

    /**
     * Obtiene los detalles de un cliente por su ID.
     *
     * @param id ID del cliente.
     * @return Respuesta con los detalles del cliente y un estado HTTP 200 (OK).
     */
    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> getClientById(@PathVariable Long id){
        ClientResponseDTO client = clientService.getClientById(id);
        return ResponseEntity.ok(client);
    }

    /**
     * Crea un nuevo cliente.
     *
     * @param clientRequestDTO Datos del cliente a crear.
     * @return Respuesta con los datos del cliente creado y un estado HTTP 201 (CREATED).
     */
    @PostMapping
    public ResponseEntity<ClientResponseDTO> createClient(@Valid @RequestBody ClientRequestDTO clientRequestDTO){
        ClientResponseDTO createdClient = clientService.createClient(clientRequestDTO);
        return new ResponseEntity<>(createdClient, HttpStatus.CREATED);
    }


    /**
     * Actualiza un cliente existente por su ID.
     *
     * @param id               ID del cliente a actualizar.
     * @param clientRequestDTO Datos actualizados del cliente.
     * @return Respuesta con los datos del cliente actualizado y un estado HTTP 200 (OK).
     */
    @PutMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> updateClient(@PathVariable Long id, @Valid @RequestBody ClientRequestDTO clientRequestDTO){
        ClientResponseDTO updatedClient = clientService.updateClient(id, clientRequestDTO);
        return ResponseEntity.ok(updatedClient);
    }

    /**
     * Elimina un cliente por su ID.
     *
     * @param id ID del cliente a eliminar.
     * @return Respuesta con un estado HTTP 204 (NO CONTENT) si la eliminación fue exitosa.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id){
        clientService.deleteClient(id);
        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
