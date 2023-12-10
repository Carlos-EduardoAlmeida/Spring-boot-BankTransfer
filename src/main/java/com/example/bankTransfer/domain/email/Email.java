package com.example.bankTransfer.domain.email;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "TB_EMAIL")
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long emailId;
    private String emailTo;
    private String subject;
    @Column(columnDefinition = "TEXT")
    private String text;
    private LocalDateTime sendDateEmail;
    private StatusEmail statusEmail;
}
