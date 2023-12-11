package com.example.bankTransfer.domain.email;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@Entity(name = "TB_EMAIL")
@Table(name = "TB_EMAIL")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class EmailMessage {
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
