package com.example.bankTransfer.services;

import com.example.bankTransfer.domain.email.EmailMessage;
import com.example.bankTransfer.domain.user.User;
import com.example.bankTransfer.domain.user.UserType;
import com.example.bankTransfer.dtos.UserDto;
import com.example.bankTransfer.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailMessageService emailMessageService;

    public User createUser(UserDto userDto){
        User newUser = new User();
        BeanUtils.copyProperties(userDto, newUser);
        return this.userRepository.save(newUser);
    }

    public String findUserByEmailAndPassword(String email, String password) throws Exception {
        Optional<User> findUser = this.userRepository.findUserByEmailAndPassword(email, password);
        return findUser.get().getId();
    }

    public void saveUser(User user){
        this.userRepository.save(user);
    }

    public void deleteUser(String id) throws Exception {
        User userDelete = this.findUserById(id);
        this.userRepository.delete(userDelete);
        System.out.println(userDelete);
    }

    public List<User> getAllUsers(){
        return this.userRepository.findAll();
    }

    public void validateTransaction(User sender, BigDecimal amount) throws Exception{
        if(sender.getUserType() == UserType.MERCHANT){
            throw new Exception("Usuário com o tipo 'logista' não poder realizar transação");
        }

        if(sender.getBalance().compareTo(amount) < 0){
            throw new Exception("Saldo insuficiente");
        }
    }

    public User findUserById(String id) throws Exception {
        return this.userRepository.findUserById(id).orElseThrow( () -> new Exception("Usuário não encontrado"));
    }

    public User findUserByDocument(String document) throws Exception {
        return this.userRepository.findUserByDocument(document).orElseThrow( () -> new Exception("Usuário não encontrado"));
    }
}
