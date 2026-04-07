package org.bankApp.dto.request;

import jakarta.validation.constraints.NotNull;
import org.bankApp.enums.Role;

public record LoginRequest(@NotNull String email, @NotNull String password) {
}
