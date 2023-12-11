package com.example.bankTransfer.dtos;

import com.example.bankTransfer.domain.user.UserType;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransactionDto(@NotNull BigDecimal value, @NotNull String senderId, @NotNull String receiverDocument) {
}