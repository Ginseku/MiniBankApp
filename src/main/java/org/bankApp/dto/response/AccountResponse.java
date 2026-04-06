package org.bankApp.dto.response;

import org.bankApp.entity.Users;

import java.math.BigDecimal;

public record AccountResponse(Long id, Long user_id, BigDecimal balance, String currency) {
}
