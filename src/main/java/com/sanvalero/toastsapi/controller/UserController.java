package com.sanvalero.toastsapi.controller;

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

    @GetMapping("/user/id={id}")
    public User getUserById(@PathVariable int id) throws NotFoundException {
        return us.findById(id);
    }

    @PostMapping("/user")
    public User create(@RequestBody User user) {
        return us.addUser(user);
    }

    @PutMapping("user/id={id}")
    public User modify(@PathVariable int id, @RequestBody User user) throws NotFoundException {
        User userToModify = us.findById(id);
        userToModify.setActive(user.isActive());
        userToModify.setBirthDate(user.getBirthDate());
        userToModify.setCreationDate(user.getCreationDate());
        userToModify.setEmail(user.getEmail());
        userToModify.setMoneySpent(user.getMoneySpent());
        userToModify.setName(user.getName());
        userToModify.setPassword(user.getPassword());
        userToModify.setPublicationsNumber(user.getPublicationsNumber());
        userToModify.setSurname(user.getSurname());
        
        return us.modifyUser(userToModify);
    }

    @DeleteMapping("/user/id={id}")
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
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException bnfe) {
        ErrorResponse errorResponse = new ErrorResponse("404", bnfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
