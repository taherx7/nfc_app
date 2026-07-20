package com.id4a.telco_backoffice.service;

import com.id4a.telco_backoffice.model.Produit;
import com.id4a.telco_backoffice.model.Revendeur;
import com.id4a.telco_backoffice.repository.ProduitRepository;
import com.id4a.telco_backoffice.repository.RevendeurRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProduitService {

    private final ProduitRepository repository;
    private final RevendeurRepository revendeurRepository;

    public ProduitService(ProduitRepository repository, RevendeurRepository revendeurRepository) {
        this.repository = repository;
        this.revendeurRepository = revendeurRepository;
    }

    public Produit create(Produit p) {
        p.setActif(true);
        return repository.save(p);
    }

    public List<Produit> findAll() {
        return repository.findAll();
    }

    public List<Produit> findByRevendeur(Long revendeurId) {
        Revendeur r = revendeurRepository.findById(revendeurId)
                .orElseThrow(() -> new RuntimeException("Revendeur introuvable"));
        return r.getCatalogue().stream().filter(Produit::getActif).toList();
    }

    public List<Produit> findAvailableAdminProducts(Long revendeurId) {
        Revendeur r = revendeurRepository.findById(revendeurId)
                .orElseThrow(() -> new RuntimeException("Revendeur introuvable"));
        List<Long> assignedIds = r.getCatalogue().stream().map(Produit::getId).toList();
        return repository.findAll().stream()
                .filter(Produit::getActif)
                .filter(p -> !assignedIds.contains(p.getId()))
                .toList();
    }

    @Transactional
    public void assignProduit(Long revendeurId, Long produitId) {
        Revendeur r = revendeurRepository.findById(revendeurId)
                .orElseThrow(() -> new RuntimeException("Revendeur introuvable"));
        Produit p = repository.findById(produitId)
                .orElseThrow(() -> new RuntimeException("Produit introuvable"));
        if (!r.getCatalogue().contains(p))
            r.getCatalogue().add(p);
        revendeurRepository.save(r);
    }

    @Transactional
    public void assignMultiple(Long revendeurId, List<Long> produitIds) {
        produitIds.forEach(id -> assignProduit(revendeurId, id));
    }

    @Transactional
    public void removeFromCatalog(Long revendeurId, Long produitId) {
        Revendeur r = revendeurRepository.findById(revendeurId)
                .orElseThrow(() -> new RuntimeException("Revendeur introuvable"));
        r.getCatalogue().removeIf(p -> p.getId().equals(produitId));
        revendeurRepository.save(r);
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

    public Produit reactivate(Long id) {
        Produit p = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit introuvable"));
        p.setActif(true);
        return repository.save(p);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}