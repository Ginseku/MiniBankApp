package org.bankApp.dto.request;

import org.bankApp.entity.Account;

import java.math.BigDecimal;

public record TransferRequest(Long from_account, Long to_account, BigDecimal amount) {
}
