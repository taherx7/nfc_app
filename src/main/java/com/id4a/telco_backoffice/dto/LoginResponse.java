package com.id4a.telco_backoffice.dto;

public class LoginResponse {
    private Long id;
    private String nom;
    private String prenom;
    private String telephone;
    private String token;

    public LoginResponse(Long id, String nom, String prenom, String telephone, String token) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getToken() {
        return token;
    }
}