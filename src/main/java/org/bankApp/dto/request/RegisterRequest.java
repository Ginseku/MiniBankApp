package org.bankApp.dto.request;

import org.bankApp.enums.Role;

public record RegisterRequest(String email, String password, String fullName) {
}
