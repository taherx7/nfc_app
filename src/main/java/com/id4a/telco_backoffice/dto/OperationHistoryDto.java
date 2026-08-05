package com.id4a.telco_backoffice.dto;

import com.id4a.telco_backoffice.model.ClientFinal;
import com.id4a.telco_backoffice.model.DetailOperation;
import com.id4a.telco_backoffice.model.Operation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OperationHistoryDto(
        Long id,
        ClientFinal clientFinal,
        BigDecimal montantTotal,
        LocalDateTime dateOperation,
        List<DetailOperation> details) {
    public static OperationHistoryDto fromEntity(Operation op) {
        return new OperationHistoryDto(
                op.getId(),
                op.getClientFinal(),
                op.getMontantTotal(),
                op.getDateOperation(),
                op.getDetails());
    }
}
