package com.id4a.telco_backoffice.controller;

import com.id4a.telco_backoffice.model.Revendeur;
import com.id4a.telco_backoffice.service.RevendeurService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/revendeurs")
public class revendeurcontroller {

    private final RevendeurService service;

    public revendeurcontroller(RevendeurService service) {
        this.service = service;
    }

    @PostMapping
    public Revendeur create(@RequestBody Revendeur r) {
        return service.create(r);
    }

    @GetMapping
    public List<Revendeur> findAll() {
        return service.findAll();
    }

    @PutMapping("/{id}/suspend")
    public Revendeur suspend(@PathVariable Long id, @RequestParam String motif) {
        return service.suspend(id, motif);
    }

    @PutMapping("/{id}/reactivate")
    public Revendeur reactivate(@PathVariable Long id) {
        return service.reactivate(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}