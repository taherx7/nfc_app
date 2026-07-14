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

    @DeleteMapping("/{id}/permanent")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PutMapping("/{id}/reactivate")
    public Produit reactivate(@PathVariable Long id) {
        return service.reactivate(id);
    }
}