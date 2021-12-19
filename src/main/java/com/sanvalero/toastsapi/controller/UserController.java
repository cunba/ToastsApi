package com.sanvalero.toastsapi.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.sanvalero.toastsapi.exception.ErrorResponse;
import com.sanvalero.toastsapi.exception.NotFoundException;
import com.sanvalero.toastsapi.model.User;
import com.sanvalero.toastsapi.service.UserService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService us;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(us.findAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getById(@PathVariable int id) throws NotFoundException {
        return new ResponseEntity<>(us.findById(id), HttpStatus.OK);
    }

    @PostMapping("/user")
    public ResponseEntity<User> create(@RequestBody User user) {
        user.setCreationDate(LocalDate.now());
        return new ResponseEntity<>(us.addUser(user), HttpStatus.OK);
    }

    @PatchMapping("/user/update-publications-number")
    public ResponseEntity<String> updatePublicationsNumber(@RequestParam(value = "id") int id)
            throws NotFoundException {

        User user = us.findById(id);
        user.setPublicationsNumber(us.countPublications(id));
        us.updatePublicationsNumber(user);

        return new ResponseEntity<>("Publications number updated.", HttpStatus.OK);
    }

    @PatchMapping("/user/update-money-spent")
    public ResponseEntity<String> updateMoneySpent(@RequestParam(value = "id") int id)
            throws NotFoundException {

        User user = us.findById(id);
        user.setMoneySpent(us.sumPrice(id));
        us.updateMoneySpent(user);

        return new ResponseEntity<>("Money spent updated.", HttpStatus.OK);
    }

    @PatchMapping("/user/update-password")
    public ResponseEntity<String> updatePassword(@RequestParam(value = "id") int id,
            @RequestBody Map<String, Object> password) throws NotFoundException {

        User user = us.findById(id);

        ModelMapper mapper = new ModelMapper();
        User userPassword = mapper.map(password, User.class);

        user.setPassword(userPassword.getPassword());
        us.updatePassword(user);

        return new ResponseEntity<>("Password updated.", HttpStatus.OK);
    }

    @PatchMapping("/user/disable")
    public ResponseEntity<String> disable(@RequestParam(value = "id") int id) throws NotFoundException {
        User user = us.findById(id);
        user.setActive(false);
        us.disable(user);

        return new ResponseEntity<>("User disabled.", HttpStatus.OK);
    }

    @PatchMapping("/user/activate")
    public ResponseEntity<String> activate(@RequestParam(value = "id") int id) throws NotFoundException {
        User user = us.findById(id);
        user.setActive(true);
        us.activate(user);

        return new ResponseEntity<>("User activated.", HttpStatus.OK);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) throws NotFoundException {
        User user = us.findById(id);
        us.deleteUser(user);
        return new ResponseEntity<>("User deleted", HttpStatus.OK);
    }

    @DeleteMapping("/users")
    public ResponseEntity<String> deleteAll() {
        us.deleteAll();
        return new ResponseEntity<>("All users deleted", HttpStatus.OK);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException nfe) {
        ErrorResponse errorResponse = new ErrorResponse("404", nfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
