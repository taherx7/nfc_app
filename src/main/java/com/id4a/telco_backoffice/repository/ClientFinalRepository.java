package com.id4a.telco_backoffice.repository;

import com.id4a.telco_backoffice.model.ClientFinal;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientFinalRepository extends JpaRepository<ClientFinal, Long> {
    Optional<ClientFinal> findByCodeNfc(String codeNfc);
}
