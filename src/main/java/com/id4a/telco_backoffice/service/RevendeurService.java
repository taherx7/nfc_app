package com.id4a.telco_backoffice.service;

import com.id4a.telco_backoffice.model.Revendeur;
import com.id4a.telco_backoffice.repository.RevendeurRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class RevendeurService {

    private final RevendeurRepository repository;
    private final PasswordEncoder passwordEncoder;

    public RevendeurService(RevendeurRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public Revendeur create(Revendeur r) {
        r.setMotDePasse(passwordEncoder.encode(r.getMotDePasse()));
        r.setStatut(Revendeur.Statut.ACTIF);
        return repository.save(r);
    }

    public List<Revendeur> findAll() {
        return repository.findAll();
    }

    public Revendeur suspend(Long id, String motif) {
        Revendeur r = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Revendeur introuvable"));
        r.setStatut(Revendeur.Statut.SUSPENDU);
        r.setMotifSuspension(motif);
        return repository.save(r);
    }

    public Revendeur reactivate(Long id) {
        Revendeur r = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Revendeur introuvable"));
        r.setStatut(Revendeur.Statut.ACTIF);
        r.setMotifSuspension(null);
        return repository.save(r);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Revendeur recharger(Long id, BigDecimal montant) {
        Revendeur r = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Revendeur introuvable"));
        if (montant == null || montant.compareTo(BigDecimal.ZERO) <= 0)
            throw new RuntimeException("Le montant doit être positif");
        BigDecimal nouveauSolde = r.getSoldeUtilise().subtract(montant);
        r.setSoldeUtilise(nouveauSolde.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : nouveauSolde);
        return repository.save(r);
    }

    public Revendeur updatePlafond(Long id, BigDecimal nouveauPlafond) {
        Revendeur r = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Revendeur introuvable"));
        if (nouveauPlafond == null || nouveauPlafond.compareTo(BigDecimal.ZERO) < 0)
            throw new RuntimeException("Le plafond doit être positif ou nul");
        r.setPlafondAutorise(nouveauPlafond);
        return repository.save(r);
    }

    public Revendeur findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Revendeur introuvable"));
    }

}