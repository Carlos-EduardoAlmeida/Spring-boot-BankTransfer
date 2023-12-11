package com.example.bankTransfer.services;

import com.example.bankTransfer.domain.email.EmailMessage;
import com.example.bankTransfer.domain.email.StatusEmail;
import com.example.bankTransfer.repository.EmailMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailMessageService {
    @Autowired
    EmailMessageRepository emailRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    public EmailMessage sendEmail(EmailMessage emailMessage){
        emailMessage.setSendDateEmail(LocalDateTime.now());
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("ceduardoeab10@gmail.com");
            message.setTo(emailMessage.getEmailTo());
            message.setSubject(emailMessage.getSubject());
            message.setText(emailMessage.getText());
            javaMailSender.send(message);

            emailMessage.setStatusEmail(StatusEmail.SENT);
        } catch (MailException e){
            emailMessage.setStatusEmail(StatusEmail.ERROR);
        } finally {
            return emailRepository.save(emailMessage);
        }
    }
}