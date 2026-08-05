package com.id4a.telco_backoffice.repository;

import com.id4a.telco_backoffice.model.Operation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {
    List<Operation> findByClientFinal_IdOrderByDateOperationDesc(Long clientFinalId);
}
