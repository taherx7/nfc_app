package com.id4a.telco_backoffice.repository;

import com.id4a.telco_backoffice.model.Produit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {
}
