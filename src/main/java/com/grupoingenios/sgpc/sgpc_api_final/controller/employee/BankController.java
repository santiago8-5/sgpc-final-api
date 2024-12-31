package com.grupoingenios.sgpc.sgpc_api_final.controller.employee;

import com.grupoingenios.sgpc.sgpc_api_final.dto.employee.BankRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.employee.BankResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.service.employee.BankService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/banks")
@Validated
public class BankController {

    private final BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @GetMapping
    public ResponseEntity<List<BankResponseDTO>> getAllBanks() {
        List<BankResponseDTO> banks = bankService.getAllBanks();
        return ResponseEntity.ok(banks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankResponseDTO> getBankById(@PathVariable long id) {
        BankResponseDTO bank = bankService.getBankById(id);
        return ResponseEntity.ok(bank);
    }

    @PostMapping
    public ResponseEntity<BankResponseDTO> createBank(@Valid @RequestBody BankRequestDTO bankDTO) {
        BankResponseDTO createdBank = bankService.createBank(bankDTO);
        return new ResponseEntity<>(createdBank, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BankResponseDTO> updatedBank(@PathVariable Long id, @Valid @RequestBody BankRequestDTO bankDTO){
        BankResponseDTO updatedBank = bankService.updateBank(id, bankDTO);
        return ResponseEntity.ok(updatedBank);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBank(@PathVariable Long id){
        bankService.deleteBank(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }




}