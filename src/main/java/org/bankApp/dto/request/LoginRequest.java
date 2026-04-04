package org.bankApp.dto.request;

import org.bankApp.enums.Role;

public record LoginRequest(String email, String password, Role role) {
}
