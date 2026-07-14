package com.id4a.telco_backoffice.controller;

import com.id4a.telco_backoffice.model.ClientFinal;
import com.id4a.telco_backoffice.service.ClientFinalService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clients")
public class ClientFinalController {

    private final ClientFinalService service;

    public ClientFinalController(ClientFinalService service) {
        this.service = service;
    }

    @PostMapping
    public ClientFinal create(@RequestBody ClientFinal c) {
        return service.create(c);
    }

    @GetMapping
    public List<ClientFinal> findAll() {
        return service.findAll();
    }

    @GetMapping("/nfc/{code}")
    public Optional<ClientFinal> findByNfc(@PathVariable String code) {
        return service.findByCodeNfc(code);
    }
}