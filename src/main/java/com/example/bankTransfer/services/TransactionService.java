package com.example.bankTransfer.services;



import com.example.bankTransfer.domain.email.EmailMessage;
import com.example.bankTransfer.domain.transaction.Transaction;
import com.example.bankTransfer.domain.user.User;
import com.example.bankTransfer.dtos.TransactionDto;
import com.example.bankTransfer.repository.TransactionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
@Service
public class TransactionService {
    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private EmailMessageService emailService;

    public Transaction createTransaction(TransactionDto transaction) throws Exception {
        User sender = this.userService.findUserById(transaction.senderId());
        User receiver = this.userService.findUserByDocument(transaction.receiverDocument());

        userService.validateTransaction(sender, transaction.value());
/*
        if(!this.authorizeTransaction()){
            throw new Exception("Permissão negada");
        }*/

        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(transaction.value());
        newTransaction.setSender(sender);
        newTransaction.setReceiver(receiver);
        newTransaction.setTimestamp(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(transaction.value()));
        receiver.setBalance(receiver.getBalance().add(transaction.value()));

        transactionRepository.save(newTransaction);
        userService.saveUser(sender);
        userService.saveUser(receiver);

        EmailMessage emailMessage = new EmailMessage();
        emailMessage.setText("Transferência teste enviada com sucesso!");
        emailMessage.setSubject("transference test");
        emailMessage.setEmailTo(sender.getEmail());
        EmailMessage newEmail = emailService.sendEmail(emailMessage);
        System.out.println(newEmail);

        return newTransaction;
    }

   /* public boolean authorizeTransaction(){
        ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity("https://run.mocky.io/v3/5794d450-d2e2-4412-8131-73d0293ac1cc", Map.class);
        return authorizationResponse.getStatusCode() == HttpStatus.OK && Objects.equals((String) authorizationResponse.getBody().get("message"), "true");
    }*/
}
