package org.bankApp.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.bankApp.enums.Role;

public record RegisterRequest(
        @NotNull @Size(min = 6, message = "Email should contain at least 6 characters") String email,
        @NotNull @Size(min = 8, message = "Password should contain at least 8 characters") String password,
        @NotNull String fullName) {
}
