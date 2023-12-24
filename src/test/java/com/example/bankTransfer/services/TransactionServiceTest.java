package com.example.bankTransfer.services;

import com.example.bankTransfer.domain.email.EmailMessage;
import com.example.bankTransfer.domain.transaction.Transaction;
import com.example.bankTransfer.domain.user.User;
import com.example.bankTransfer.domain.user.UserType;
import com.example.bankTransfer.dtos.TransactionDto;
import com.example.bankTransfer.dtos.UserDto;
import com.example.bankTransfer.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class TransactionServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private EmailMessageService emailService;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTransaction() throws Exception {
        String senderId = "123";
        String receiverDocument = "123456789";

        TransactionDto transactionDto = new TransactionDto(BigDecimal.valueOf(200), senderId, receiverDocument);

        UserDto senderDto = new UserDto("carlos", "eduardo", "11111111", "teste@gmail.com", "senhateste", UserType.COMMON);
        User sender = new User();
        BeanUtils.copyProperties(senderDto, sender);
        sender.setId(senderId);
        sender.setBalance(BigDecimal.valueOf(2000));

        UserDto receiverDto = new UserDto("carlos", "eduardo", receiverDocument, "teste@gmail.com", "senhateste", UserType.COMMON);
        User receiver = new User();
        receiver.setBalance(BigDecimal.valueOf(1500));
        BeanUtils.copyProperties(receiverDto, receiver);

        when(userService.findUserById(any())).thenReturn(sender);
        when(userService.findUserByDocument(receiverDocument)).thenReturn(receiver);

        when(transactionRepository.save(any())).thenReturn(new Transaction());

        when(emailService.sendEmail(any())).thenReturn(new EmailMessage());

        Transaction createdTransaction = transactionService.createTransaction(transactionDto);

        assertNotNull(createdTransaction);
        assertEquals(createdTransaction.getSender().getBalance(), (BigDecimal.valueOf(2000).subtract(transactionDto.value())));
        assertEquals(createdTransaction.getReceiver().getBalance(), BigDecimal.valueOf(1500).add(transactionDto.value()));
    }
}