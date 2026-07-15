package com.id4a.telco_backoffice.controller;

import com.id4a.telco_backoffice.model.Produit;
import com.id4a.telco_backoffice.service.ProduitService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produits")
public class ProduitController {

    private final ProduitService service;

    public ProduitController(ProduitService service) {
        this.service = service;
    }

    @PostMapping
    public Produit create(@RequestBody Produit p) {
        return service.create(p);
    }

    @GetMapping
    public List<Produit> findAll() {
        return service.findAll();
    }

    @PutMapping("/{id}")
    public Produit update(@PathVariable Long id, @RequestBody Produit p) {
        return service.update(id, p);
    }

    @DeleteMapping("/{id}")
    public void deactivate(@PathVariable Long id) {
        service.deactivate(id);
    }

    @PutMapping("/{id}/reactivate")
    public Produit reactivate(@PathVariable Long id) {
        return service.reactivate(id);
    }

    @DeleteMapping("/{id}/permanent")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    // revendeur catalog endpoints
    @GetMapping("/revendeur/{revendeurId}")
    public List<Produit> getByRevendeur(@PathVariable Long revendeurId) {
        return service.findByRevendeur(revendeurId);
    }

    @PostMapping("/revendeur/{revendeurId}")
    public Produit createByRevendeur(@PathVariable Long revendeurId, @RequestBody Produit p) {
        return service.createByRevendeur(p, revendeurId);
    }

    @PostMapping("/revendeur/{revendeurId}/assign/{produitId}")
    public void assign(@PathVariable Long revendeurId, @PathVariable Long produitId) {
        service.assignProduit(revendeurId, produitId);
    }

    @PostMapping("/revendeur/{revendeurId}/assign-multiple")
    public void assignMultiple(@PathVariable Long revendeurId, @RequestBody List<Long> produitIds) {
        service.assignMultiple(revendeurId, produitIds);
    }

    @DeleteMapping("/revendeur/{revendeurId}/remove-admin/{produitId}")
    public void removeAdmin(@PathVariable Long revendeurId, @PathVariable Long produitId) {
        service.removeAdminProduit(revendeurId, produitId);
    }

    @DeleteMapping("/revendeur/{revendeurId}/remove-custom/{produitId}")
    public void removeCustom(@PathVariable Long revendeurId, @PathVariable Long produitId) {
        service.removeCustomProduit(revendeurId, produitId);
    }
}