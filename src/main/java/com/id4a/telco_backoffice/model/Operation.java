package com.id4a.telco_backoffice.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "operation")
@Data
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "revendeur_id", nullable = false)
    private Revendeur revendeur;

    @ManyToOne
    @JoinColumn(name = "client_final_id", nullable = false)
    private ClientFinal clientFinal;

    @Column(name = "montant_total", nullable = false)
    private BigDecimal montantTotal;

    @Column(name = "date_operation", updatable = false)
    private LocalDateTime dateOperation = LocalDateTime.now();

    @OneToMany(mappedBy = "operation", cascade = CascadeType.ALL)
    private List<DetailOperation> details;
}
