package com.id4a.telco_backoffice.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "produit")
@Data
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom_service", nullable = false)
    private String nomService;

    private String description;

    @Column(name = "categorie_service")
    private String categorieService;

    @Column(name = "prix_achat_revendeur", nullable = false)
    private BigDecimal prixAchatRevendeur;

    @Column(name = "prix_vente_client", nullable = false)
    private BigDecimal prixVenteClient;

    private Boolean actif = true;

    @Column(name = "date_creation", updatable = false)
    private LocalDateTime dateCreation = LocalDateTime.now();
}
