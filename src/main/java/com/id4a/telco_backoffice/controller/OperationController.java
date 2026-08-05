package com.id4a.telco_backoffice.controller;

import com.id4a.telco_backoffice.dto.OperationRequest;
import com.id4a.telco_backoffice.model.Operation;
import com.id4a.telco_backoffice.service.OperationService;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/operations")
public class OperationController {

    private final OperationService service;

    public OperationController(OperationService service) {
        this.service = service;
    }

    @PostMapping
    public Operation create(@RequestBody OperationRequest request) {
        return service.create(request);
    }

    @GetMapping
    public List<Operation> findAll() {
        return service.findAll();
    }

    @GetMapping("/filter")
    public List<Operation> filter(
            @RequestParam(required = false) String ville,
            @RequestParam(required = false) String operateur,
            @RequestParam(required = false) Long revendeurId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateDebut,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFin) {
        return service.filter(ville, operateur, revendeurId, dateDebut, dateFin);
    }

    // OperationController.java — add this endpoint
    @GetMapping("/client/{clientFinalId}")
    public List<Operation> findByClient(@PathVariable Long clientFinalId) {
        return service.getByClientId(clientFinalId);
    }

    // Public endpoint for consumer app — no auth needed, identified by NFC code
    @GetMapping("/nfc/{codeNfc}")
    public List<com.id4a.telco_backoffice.dto.OperationHistoryDto> findByNfcCode(@PathVariable String codeNfc) {
        return service.getByNfcCode(codeNfc).stream()
                .map(com.id4a.telco_backoffice.dto.OperationHistoryDto::fromEntity)
                .toList();
    }
}