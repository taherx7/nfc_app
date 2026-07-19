package com.id4a.telco_backoffice.service;

import com.id4a.telco_backoffice.dto.OperationRequest;
import com.id4a.telco_backoffice.model.*;
import com.id4a.telco_backoffice.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OperationService {

    private final OperationRepository operationRepository;
    private final RevendeurRepository revendeurRepository;
    private final ClientFinalRepository clientFinalRepository;
    private final ProduitRepository produitRepository;

    public OperationService(OperationRepository operationRepository,
            RevendeurRepository revendeurRepository,
            ClientFinalRepository clientFinalRepository,
            ProduitRepository produitRepository) {
        this.operationRepository = operationRepository;
        this.revendeurRepository = revendeurRepository;
        this.clientFinalRepository = clientFinalRepository;
        this.produitRepository = produitRepository;
    }

    @Transactional
    public Operation create(OperationRequest req) {
        Revendeur r = revendeurRepository.findById(req.getRevendeurId())
                .orElseThrow(() -> new RuntimeException("Revendeur introuvable"));

        if (r.getStatut() == Revendeur.Statut.SUSPENDU) {
            throw new RuntimeException(
                    "Compte suspendu — impossible d'effectuer une vente. Motif: " + r.getMotifSuspension());
        }

        ClientFinal c = clientFinalRepository.findById(req.getClientFinalId())
                .orElseThrow(() -> new RuntimeException("Client introuvable"));

        Operation op = new Operation();
        op.setRevendeur(r);
        op.setClientFinal(c);

        List<DetailOperation> details = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (OperationRequest.LigneProduit ligne : req.getProduits()) {
            Produit p = produitRepository.findById(ligne.getProduitId())
                    .orElseThrow(() -> new RuntimeException("Produit introuvable: " + ligne.getProduitId()));

            DetailOperation d = new DetailOperation();
            d.setOperation(op);
            d.setProduit(p);
            d.setQuantite(ligne.getQuantite());
            d.setPrixUnitaire(p.getPrixVenteClient());
            details.add(d);

            total = total.add(p.getPrixVenteClient().multiply(BigDecimal.valueOf(ligne.getQuantite())));
        }

        op.setMontantTotal(total);
        op.setDetails(details);

        return operationRepository.save(op);
    }

    public List<Operation> findAll() {
        return operationRepository.findAll();
    }

    public List<Operation> filter(String ville, String operateur, Long revendeurId,
            LocalDateTime dateDebut, LocalDateTime dateFin) {
        return operationRepository.findAll().stream()
                .filter(op -> ville == null || ville.equalsIgnoreCase(op.getRevendeur().getVille()))
                .filter(op -> operateur == null
                        || operateur.equalsIgnoreCase(op.getClientFinal().getOperateur().name()))
                .filter(op -> revendeurId == null || revendeurId.equals(op.getRevendeur().getId()))
                .filter(op -> dateDebut == null || !op.getDateOperation().isBefore(dateDebut))
                .filter(op -> dateFin == null || !op.getDateOperation().isAfter(dateFin))
                .toList();
    }
}