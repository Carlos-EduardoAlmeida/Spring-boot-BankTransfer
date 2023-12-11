package com.example.bankTransfer.repository;

import com.example.bankTransfer.domain.email.EmailMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailMessageRepository extends JpaRepository<EmailMessage, Long> {
}
