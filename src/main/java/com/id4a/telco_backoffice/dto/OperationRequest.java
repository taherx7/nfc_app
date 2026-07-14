package com.id4a.telco_backoffice.dto;

import java.util.List;

public class OperationRequest {
    private Long revendeurId;
    private Long clientFinalId;
    private List<LigneProduit> produits;

    public static class LigneProduit {
        private Long produitId;
        private Integer quantite;

        public Long getProduitId() {
            return produitId;
        }

        public void setProduitId(Long produitId) {
            this.produitId = produitId;
        }

        public Integer getQuantite() {
            return quantite;
        }

        public void setQuantite(Integer quantite) {
            this.quantite = quantite;
        }
    }

    public Long getRevendeurId() {
        return revendeurId;
    }

    public void setRevendeurId(Long revendeurId) {
        this.revendeurId = revendeurId;
    }

    public Long getClientFinalId() {
        return clientFinalId;
    }

    public void setClientFinalId(Long clientFinalId) {
        this.clientFinalId = clientFinalId;
    }

    public List<LigneProduit> getProduits() {
        return produits;
    }

    public void setProduits(List<LigneProduit> produits) {
        this.produits = produits;
    }
}