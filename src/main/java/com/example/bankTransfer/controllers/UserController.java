package com.example.bankTransfer.controllers;

import com.example.bankTransfer.domain.user.User;
import com.example.bankTransfer.dtos.LoginDto;
import com.example.bankTransfer.dtos.UserDto;
import com.example.bankTransfer.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<User> createUser(@RequestBody UserDto userDto) throws Exception {
        User newUser = userService.createUser(userDto);
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginDto data) throws Exception {
        return ResponseEntity.ok(userService.findUserByEmailAndPassword(data.email(), data.password()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") String id){
        try{
            userService.deleteUser(id);
            System.out.println(id);
            return ResponseEntity.ok().build();
        }catch (Exception exception){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

}
