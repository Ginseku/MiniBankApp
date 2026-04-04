package org.bankApp.dto.response;

import org.bankApp.entity.Account;
import org.bankApp.enums.TransactionStatus;
import org.bankApp.enums.TransactionType;

import java.math.BigDecimal;

public record TransactionResponse (Account from_account, Account to_account, BigDecimal amount, TransactionStatus status, TransactionType type){
}
