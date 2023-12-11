package com.example.bankTransfer.dtos;

import com.example.bankTransfer.domain.user.UserType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record UserDto(@NotBlank String firstName, @NotBlank String lastName, @NotBlank String document, @NotBlank String email, @NotBlank String password, @NotBlank UserType userType) {
}
