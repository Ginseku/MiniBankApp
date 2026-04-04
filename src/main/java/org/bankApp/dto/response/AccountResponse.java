package org.bankApp.dto.response;

import org.bankApp.entity.Users;

import java.math.BigDecimal;

public record AccountResponse(Users users, BigDecimal balance, String currency) {
}
