package com.id4a.telco_backoffice.service;

import com.id4a.telco_backoffice.model.Produit;
import com.id4a.telco_backoffice.model.Revendeur;
import com.id4a.telco_backoffice.repository.ProduitRepository;
import com.id4a.telco_backoffice.repository.RevendeurRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProduitService {

    private final ProduitRepository repository;
    private final RevendeurRepository revendeurRepository;

    public ProduitService(ProduitRepository repository, RevendeurRepository revendeurRepository) {
        this.repository = repository;
        this.revendeurRepository = revendeurRepository;
    }

    // admin creates global product
    public Produit create(Produit p) {
        p.setActif(true);
        p.setCreePar(Produit.CreePar.ADMIN);
        p.setRevendeurCreateur(null);
        return repository.save(p);
    }

    // revendeur creates custom product — auto added to his catalog
    @Transactional
    public Produit createByRevendeur(Produit p, Long revendeurId) {
        Revendeur r = revendeurRepository.findById(revendeurId)
                .orElseThrow(() -> new RuntimeException("Revendeur introuvable"));
        if (r.getStatut() == Revendeur.Statut.SUSPENDU)
            throw new RuntimeException("Compte suspendu — création de produit impossible");
        p.setActif(true);
        p.setCreePar(Produit.CreePar.REVENDEUR);
        p.setRevendeurCreateur(r);
        Produit saved = repository.save(p);
        r.getCatalogue().add(saved);
        revendeurRepository.save(r);
        return saved;
    }

    public List<Produit> findAvailableAdminProducts(Long revendeurId) {
        Revendeur r = revendeurRepository.findById(revendeurId)
                .orElseThrow(() -> new RuntimeException("Revendeur introuvable"));
        List<Long> assignedIds = r.getCatalogue().stream().map(Produit::getId).toList();
        return repository.findAll().stream()
                .filter(p -> p.getCreePar() == Produit.CreePar.ADMIN)
                .filter(Produit::getActif)
                .filter(p -> !assignedIds.contains(p.getId()))
                .toList();
    }

    public List<Produit> findAll() {
        return repository.findAll();
    }

    // returns assigned ADMIN products + revendeur's own custom products
    public List<Produit> findByRevendeur(Long revendeurId) {
        Revendeur r = revendeurRepository.findById(revendeurId)
                .orElseThrow(() -> new RuntimeException("Revendeur introuvable"));
        List<Produit> result = new ArrayList<>(r.getCatalogue());
        repository.findAll().stream()
                .filter(p -> p.getCreePar() == Produit.CreePar.REVENDEUR
                        && p.getRevendeurCreateur() != null
                        && p.getRevendeurCreateur().getId().equals(revendeurId)
                        && p.getActif())
                .forEach(result::add);
        return result.stream().filter(Produit::getActif).distinct().toList();
    }

    // assign single ADMIN product to revendeur catalog
    @Transactional
    public void assignProduit(Long revendeurId, Long produitId) {
        Revendeur r = revendeurRepository.findById(revendeurId)
                .orElseThrow(() -> new RuntimeException("Revendeur introuvable"));
        Produit p = repository.findById(produitId)
                .orElseThrow(() -> new RuntimeException("Produit introuvable"));
        if (p.getCreePar() != Produit.CreePar.ADMIN)
            throw new RuntimeException("Seuls les produits admin peuvent être assignés manuellement");
        if (!r.getCatalogue().contains(p))
            r.getCatalogue().add(p);
        revendeurRepository.save(r);
    }

    // assign multiple ADMIN products at once
    @Transactional
    public void assignMultiple(Long revendeurId, List<Long> produitIds) {
        produitIds.forEach(id -> assignProduit(revendeurId, id));
    }

    // remove ADMIN product from catalog (unlink only)
    @Transactional
    public void removeAdminProduit(Long revendeurId, Long produitId) {
        Revendeur r = revendeurRepository.findById(revendeurId)
                .orElseThrow(() -> new RuntimeException("Revendeur introuvable"));
        r.getCatalogue().removeIf(p -> p.getId().equals(produitId));
        revendeurRepository.save(r);
    }

    // remove custom product (permanent delete)
    @Transactional
    public void removeCustomProduit(Long revendeurId, Long produitId) {
        Revendeur r = revendeurRepository.findById(revendeurId)
                .orElseThrow(() -> new RuntimeException("Revendeur introuvable"));
        Produit p = repository.findById(produitId)
                .orElseThrow(() -> new RuntimeException("Produit introuvable"));
        if (p.getCreePar() != Produit.CreePar.REVENDEUR)
            throw new RuntimeException("Ce produit n'est pas un produit personnalisé");
        if (!p.getRevendeurCreateur().getId().equals(revendeurId))
            throw new RuntimeException("Ce produit n'appartient pas à ce revendeur");
        r.getCatalogue().removeIf(cp -> cp.getId().equals(produitId));
        revendeurRepository.save(r);
        repository.delete(p);
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