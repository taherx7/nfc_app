package com.id4a.telco_backoffice.controller;

import com.id4a.telco_backoffice.dto.LoginRequest;
import com.id4a.telco_backoffice.dto.LoginResponse;
import com.id4a.telco_backoffice.model.Revendeur;
import com.id4a.telco_backoffice.repository.RevendeurRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final RevendeurRepository revendeurRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(RevendeurRepository revendeurRepository, PasswordEncoder passwordEncoder) {
        this.revendeurRepository = revendeurRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/revendeur/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        Optional<Revendeur> opt = revendeurRepository.findByEmail(req.getEmail());
        if (opt.isEmpty())
            return ResponseEntity.status(401).body(java.util.Map.of("error", "Email introuvable"));

        Revendeur r = opt.get();
        if (!passwordEncoder.matches(req.getMotDePasse(), r.getMotDePasse()))
            return ResponseEntity.status(401).body(java.util.Map.of("error", "Mot de passe incorrect"));

        if (r.getStatut() == Revendeur.Statut.SUSPENDU)
            return ResponseEntity.status(403)
                    .body(java.util.Map.of("error", "Compte suspendu: " + r.getMotifSuspension()));

        // simple token: Base64(email:motDePasse) — good enough for a demo
        String token = Base64.getEncoder().encodeToString(
                (req.getEmail() + ":" + req.getMotDePasse()).getBytes());
        return ResponseEntity.ok(new LoginResponse(r.getId(), r.getNom(), r.getPrenom(), r.getEmail(), token));
    }
}