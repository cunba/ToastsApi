package com.sanvalero.toastsapi.controller;

import java.time.LocalDate;
import java.util.List;

import com.sanvalero.toastsapi.exception.ErrorResponse;
import com.sanvalero.toastsapi.exception.NotFoundException;
import com.sanvalero.toastsapi.model.User;
import com.sanvalero.toastsapi.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService us;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return us.findAllUsers();
    }

    @GetMapping("/user/{id}")
    public User getById(@PathVariable int id) throws NotFoundException {
        return us.findById(id);
    }

    @PostMapping("/user")
    public User create(@RequestBody User user) {
        user.setCreationDate(LocalDate.now());
        return us.addUser(user);
    }

    @PutMapping("user/{id}")
    public User update(@PathVariable int id, @RequestBody User user) throws NotFoundException {
        User userToUpdate = us.findById(id);
        userToUpdate.setActive(user.isActive());
        userToUpdate.setBirthDate(user.getBirthDate());
        userToUpdate.setCreationDate(user.getCreationDate());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setMoneySpent(user.getMoneySpent());
        userToUpdate.setName(user.getName());
        userToUpdate.setPassword(user.getPassword());
        userToUpdate.setPublicationsNumber(user.getPublicationsNumber());
        userToUpdate.setSurname(user.getSurname());
        
        return us.modifyUser(userToUpdate);
    }

    @DeleteMapping("/user/{id}")
    public User delete(@PathVariable int id) throws NotFoundException {
        User user = us.findById(id);
        us.deleteUser(user);
        return user;
    }

    @DeleteMapping("/users")
    public String deleteAll() {
        us.deleteAll();

        return "All users deleted";
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException nfe) {
        ErrorResponse errorResponse = new ErrorResponse("404", nfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
