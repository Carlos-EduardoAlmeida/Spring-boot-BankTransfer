package com.example.bankTransfer.services;

import com.example.bankTransfer.domain.user.User;
import com.example.bankTransfer.domain.user.UserType;
import com.example.bankTransfer.dtos.UserDto;
import com.example.bankTransfer.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateUser(){
        UserDto userDto = new UserDto("carlos", "eduardo", "11111111", "teste@gmail.com", "senhateste", UserType.COMMON);
        User newUser = new User();
        BeanUtils.copyProperties(userDto, newUser);

        when(userRepository.save(any(User.class))).thenReturn(newUser);

        User createdUser = userService.createUser(userDto);

        assertNotNull(createdUser);
        assertEquals(createdUser, newUser);
    }

    @Test
    void testFindUserByEmailAndPassword() throws Exception {
        UserDto newUserDto = new UserDto("carlos", "eduardo", "11111111", "teste@gmail.com", "senhateste", UserType.COMMON);
        User newUser = new User();
        BeanUtils.copyProperties(newUserDto, newUser);

        when(userRepository.findUserByEmailAndPassword(newUserDto.email(), newUser.getPassword()))
                .thenReturn(Optional.of(newUser));
        User userFound = userService.findUserByEmailAndPassword(newUser.getEmail(), newUser.getPassword());

        assertNotNull(userFound);
        assertEquals(userFound, newUser);
    }

    @Test
    @DisplayName("should validate because the balance is sufficient")
    void testValidateTransactionCase01() throws Exception {
        User sender = new User();
        sender.setBalance(BigDecimal.valueOf(1000));
        BigDecimal amount = BigDecimal.valueOf(200);

        userService.validateTransaction(sender, amount);
    }

    @Test
    @DisplayName("should not validate because the balance isn't sufficient")
    void testValidateTransactionCase02(){
        User sender = new User();
        sender.setBalance(BigDecimal.valueOf(1000));
        BigDecimal amount = BigDecimal.valueOf(2000);

        assertThrows(Exception.class, () -> userService.validateTransaction(sender, amount),
                "Exceção por saldo insuficiente");
    }

    @Test
    @DisplayName("Should find User for the given ID")
    void testFindUserByIdCase01() throws Exception {
        String userId = "123";
        User expectedUser = new User();
        UserDto userDto = new UserDto("carlos", "eduardo", "11111111", "teste@gmail.com", "senhateste", UserType.COMMON);
        BeanUtils.copyProperties(userDto, expectedUser);
        expectedUser.setId(userId);

        Mockito.when(userRepository.findUserById(userId)).thenReturn(Optional.of(expectedUser));

        User foundUser = userService.findUserById(userId);

        assertNotNull(foundUser);
        assertEquals(expectedUser, foundUser);
    }

    @Test
    @DisplayName("Shouldn't find User for the given ID")
    void testFindUserByIdCase02() {
        String userId = "123";

        Mockito.when(userRepository.findUserById(userId)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> userService.findUserById(userId),
                "Should throw an exception for user not found");
    }

    @Test
    @DisplayName("Should successfully find user with this Document")
    void testFindUserByDocumentCase01() throws Exception {
        String document = "123456789";
        User expectedUser = new User(); // Initialize with required data

        Mockito.when(userRepository.findUserByDocument(document)).thenReturn(Optional.of(expectedUser));

        User foundUser = userService.findUserByDocument(document);

        assertNotNull(foundUser);
        assertEquals(expectedUser, foundUser);
    }

    @Test
    @DisplayName("Shouldn't find user with this Document")
    void testFindUserByDocumentCase02() {
        String document = "123456789";

        Mockito.when(userRepository.findUserByDocument(document)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> userService.findUserByDocument(document),
                "Should throw an exception for user not found");
    }


}