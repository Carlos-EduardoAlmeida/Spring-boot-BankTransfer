package com.example.bankTransfer.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailMessageDto(@NotBlank @Email String emailTo, @NotBlank String subject, @NotBlank String text) {
}
