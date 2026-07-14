package com.id4a.telco_backoffice.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "revendeur")
@Data
public class Revendeur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String telephone;

    @Column(name = "mot_de_passe", nullable = false)
    private String motDePasse;

    private String adresse;
    private String ville;

    @Column(name = "plafond_autorise")
    private BigDecimal plafondAutorise = BigDecimal.ZERO;

    private String categorie;

    @Enumerated(EnumType.STRING)
    private Statut statut = Statut.ACTIF;

    @Column(name = "motif_suspension")
    private String motifSuspension;

    @Column(name = "date_creation", updatable = false)
    private LocalDateTime dateCreation = LocalDateTime.now();

    public enum Statut {
        ACTIF, SUSPENDU
    }
}
