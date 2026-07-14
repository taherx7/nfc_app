package com.id4a.telco_backoffice.service;

import com.id4a.telco_backoffice.model.ClientFinal;
import com.id4a.telco_backoffice.repository.ClientFinalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientFinalService {

    private final ClientFinalRepository repository;

    public ClientFinalService(ClientFinalRepository repository) {
        this.repository = repository;
    }

    public ClientFinal create(ClientFinal c) {
        return repository.save(c);
    }

    public List<ClientFinal> findAll() {
        return repository.findAll();
    }

    public Optional<ClientFinal> findByCodeNfc(String codeNfc) {
        return repository.findByCodeNfc(codeNfc);
    }
}