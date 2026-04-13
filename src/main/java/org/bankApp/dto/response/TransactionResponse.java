package org.bankApp.dto.response;

import org.bankApp.entity.Account;
import org.bankApp.enums.TransactionStatus;
import org.bankApp.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponse (Long from_account, Long to_account, BigDecimal amount, TransactionStatus status, TransactionType type, LocalDateTime createdAt){
}
