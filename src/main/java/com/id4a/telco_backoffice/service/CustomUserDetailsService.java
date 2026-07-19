package com.id4a.telco_backoffice.service;

import com.id4a.telco_backoffice.model.Admin;
import com.id4a.telco_backoffice.model.Revendeur;
import com.id4a.telco_backoffice.repository.AdminRepository;
import com.id4a.telco_backoffice.repository.RevendeurRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;
    private final RevendeurRepository revendeurRepository;

    public CustomUserDetailsService(AdminRepository adminRepository, RevendeurRepository revendeurRepository) {
        this.adminRepository = adminRepository;
        this.revendeurRepository = revendeurRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Admin> admin = adminRepository.findByUsername(username);
        if (admin.isPresent()) {
            return User.builder()
                    .username(admin.get().getUsername())
                    .password(admin.get().getMotDePasse())
                    .roles("ADMIN")
                    .build();
        }

        Optional<Revendeur> revendeur = revendeurRepository.findByEmail(username);
        if (revendeur.isPresent()) {
            return User.builder()
                    .username(revendeur.get().getEmail())
                    .password(revendeur.get().getMotDePasse())
                    .roles("REVENDEUR")
                    .build();
        }

        throw new UsernameNotFoundException("Utilisateur introuvable: " + username);
    }
}