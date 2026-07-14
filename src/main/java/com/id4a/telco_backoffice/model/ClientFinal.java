package com.id4a.telco_backoffice.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "client_final")
@Data
public class ClientFinal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_telephone", nullable = false, unique = true)
    private String numeroTelephone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Operateur operateur;

    @Column(name = "code_nfc", nullable = false, unique = true, length = 64)
    private String codeNfc;

    @Column(name = "date_creation", updatable = false)
    private LocalDateTime dateCreation = LocalDateTime.now();

    public enum Operateur {
        ORANGE, TT, OOREDOO
    }
}
