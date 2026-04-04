package org.bankApp.dto.request;

import org.bankApp.entity.Account;

import java.math.BigDecimal;

public record TransferRequest(Account from, Account to, BigDecimal amount) {
}
