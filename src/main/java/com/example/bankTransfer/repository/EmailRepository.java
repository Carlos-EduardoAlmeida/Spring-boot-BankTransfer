package com.example.bankTransfer.repository;

import com.example.bankTransfer.domain.email.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<Email, Long> {
}
