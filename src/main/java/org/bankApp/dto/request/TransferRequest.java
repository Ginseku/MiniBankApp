package org.bankApp.dto.request;

import jakarta.validation.constraints.NotNull;
import org.bankApp.entity.Account;
import org.bankApp.enums.TransactionType;

import java.math.BigDecimal;

public record TransferRequest(@NotNull Long from_account, @NotNull Long to_account, @NotNull BigDecimal amount, @NotNull TransactionType type, @NotNull String currency) {
}
