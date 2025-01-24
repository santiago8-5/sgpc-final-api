package com.grupoingenios.sgpc.sgpc_api_final.controller.work;

import com.grupoingenios.sgpc.sgpc_api_final.dto.work.ClientWorkResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.service.work.ClientWorkService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controlador REST para gestionar las relaciones entre clientes y obras.
 * Proporciona un endpoint para obtener la lista de relaciones entre clientes y obras.
 */
@RestController
@RequestMapping("/api/v1/client-works")
@Validated
public class ClientWorkController {

    private final ClientWorkService clientWorkService;

    /**
     * Constructor para inyectar el servicio de relaciones cliente-obra.
     *
     * @param clientWorkService Servicio que contiene la lógica de negocio para las relaciones cliente-obra.
     */
    public ClientWorkController(ClientWorkService clientWorkService) {
        this.clientWorkService = clientWorkService;
    }

    /**
     * Obtiene la lista de todas las relaciones entre clientes y obras.
     *
     * @return Respuesta con la lista de relaciones cliente-obra y un estado HTTP 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<ClientWorkResponseDTO>> getAllClientWork(){
        List<ClientWorkResponseDTO> clientWorks  = clientWorkService.getAllClientWork();
        return ResponseEntity.ok(clientWorks);
    }

}
