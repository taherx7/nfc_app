package com.id4a.telco_backoffice.service;

import com.id4a.telco_backoffice.model.Produit;
import com.id4a.telco_backoffice.model.Revendeur;
import com.id4a.telco_backoffice.repository.ProduitRepository;
import com.id4a.telco_backoffice.repository.RevendeurRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class ProduitService {

    private final ProduitRepository repository;
    private final RevendeurRepository RevendeurRepository;

    public ProduitService(ProduitRepository repository, RevendeurRepository RevendeurRepository) {
        this.repository = repository;
        this.RevendeurRepository = RevendeurRepository;
    }

    public Produit create(Produit p) {
        p.setActif(true);
        p.setCreePar(Produit.CreePar.ADMIN);
        p.setRevendeurCreateur(null);
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

    public Produit createByRevendeur(Produit p, Long revendeurId) {
        Revendeur r = RevendeurRepository.findById(revendeurId)
                .orElseThrow(() -> new RuntimeException("Revendeur introuvable"));
        if (r.getStatut() == Revendeur.Statut.SUSPENDU) {
            throw new RuntimeException("Compte suspendu — création de produit impossible");
        }
        p.setActif(true);
        p.setCreePar(Produit.CreePar.REVENDEUR);
        p.setRevendeurCreateur(r);
        return repository.save(p);
    }

    public List<Produit> findByRevendeur(Long revendeurId) {
        return repository.findAll().stream()
                .filter(p -> p.getActif())
                .filter(p -> p.getCreePar() == Produit.CreePar.ADMIN ||
                        (p.getRevendeurCreateur() != null && p.getRevendeurCreateur().getId().equals(revendeurId)))
                .toList();
    }
}
