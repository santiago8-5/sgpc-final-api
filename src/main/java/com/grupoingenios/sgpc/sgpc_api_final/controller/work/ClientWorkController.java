package com.grupoingenios.sgpc.sgpc_api_final.controller.work;

import com.grupoingenios.sgpc.sgpc_api_final.dto.work.ClientWorkResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.service.work.ClientWorkService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/v1/client-works")
@Validated
public class ClientWorkController {

    private final ClientWorkService clientWorkService;

    public ClientWorkController(ClientWorkService clientWorkService) {
        this.clientWorkService = clientWorkService;
    }

    @GetMapping
    public ResponseEntity<List<ClientWorkResponseDTO>> getAllClientWork(){
        List<ClientWorkResponseDTO> clientWorks  = clientWorkService.getAllClientWork();
        return ResponseEntity.ok(clientWorks);
    }

}
