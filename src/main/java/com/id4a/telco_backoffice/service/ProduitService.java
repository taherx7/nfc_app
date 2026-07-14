package com.id4a.telco_backoffice.service;

import com.id4a.telco_backoffice.model.Produit;
import com.id4a.telco_backoffice.repository.ProduitRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProduitService {

    private final ProduitRepository repository;

    public ProduitService(ProduitRepository repository) {
        this.repository = repository;
    }

    public Produit create(Produit p) {
        p.setActif(true);
        return repository.save(p);
    }

    public List<Produit> findAll() {
        return repository.findAll();
    }

    public Produit update(Long id, Produit updated) {
        Produit p = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit introuvable"));
        p.setNomService(updated.getNomService());
        p.setDescription(updated.getDescription());
        p.setCategorieService(updated.getCategorieService());
        p.setPrixAchatRevendeur(updated.getPrixAchatRevendeur());
        p.setPrixVenteClient(updated.getPrixVenteClient());
        return repository.save(p);
    }

    public void deactivate(Long id) {
        Produit p = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit introuvable"));
        p.setActif(false);
        repository.save(p);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Produit reactivate(Long id) {
        Produit p = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit introuvable"));
        p.setActif(true);
        return repository.save(p);
    }
}
