package com.sanvalero.toastsapi.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.sanvalero.toastsapi.exception.ErrorResponse;
import com.sanvalero.toastsapi.exception.NotFoundException;
import com.sanvalero.toastsapi.model.User;
import com.sanvalero.toastsapi.service.UserService;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.client.HttpClientErrorException.BadRequest;

@RestController
public class UserController {
    @Autowired
    private UserService us;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(us.findAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getById(@PathVariable int id) throws NotFoundException {
        return new ResponseEntity<>(us.findById(id), HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<User> create(@RequestBody User user) {
        user.setCreationDate(LocalDate.now());
        return new ResponseEntity<>(us.addUser(user), HttpStatus.OK);
    }

    @PatchMapping("/users/publications-number/")
    public ResponseEntity<String> updatePublicationsNumber(@RequestParam(value = "id") int id)
            throws NotFoundException {

        logger.info("begin update publications number");
        User user = us.findById(id);
        logger.info("User found: " + user.getId());
        user.setPublicationsNumber(us.countPublications(id));
        us.updatePublicationsNumber(user);
        logger.info("User publication number updated");
        logger.info("end update publications number");

        return new ResponseEntity<>("Publications number updated.", HttpStatus.OK);
    }

    @PatchMapping("/users/money-spent/")
    public ResponseEntity<String> updateMoneySpent(@RequestParam(value = "id") int id)
            throws NotFoundException {

        logger.info("begin update money spent");
        User user = us.findById(id);
        logger.info("USer found: " + user.getId());
        user.setMoneySpent(us.sumPrice(id));
        us.updateMoneySpent(user);
        logger.info("User money spent updated");
        logger.info("end update money spent");

        return new ResponseEntity<>("Money spent updated.", HttpStatus.OK);
    }

    @PatchMapping("/users/password/")
    public ResponseEntity<String> updatePassword(@RequestParam(value = "id") int id,
            @RequestBody Map<String, Object> password) throws NotFoundException {

        logger.info("begin update password");
        User user = us.findById(id);
        logger.info("User found: " + user.getId());
        ModelMapper mapper = new ModelMapper();
        User userPassword = mapper.map(password, User.class);
        logger.info("User mapped");
        user.setPassword(userPassword.getPassword());
        us.updatePassword(user);
        logger.info("User password updated");
        logger.info("end update password");

        return new ResponseEntity<>("Password updated.", HttpStatus.OK);
    }

    @PatchMapping("/users/disable/")
    public ResponseEntity<String> disable(@RequestParam(value = "id") int id) throws NotFoundException {
        logger.info("begin disable user");
        User user = us.findById(id);
        logger.info("User found: " + user.getId());
        user.setActive(false);
        us.disable(user);
        logger.info("User disabled");
        logger.info("end disable user");

        return new ResponseEntity<>("User disabled.", HttpStatus.OK);
    }

    @PatchMapping("/users/activate/")
    public ResponseEntity<String> activate(@RequestParam(value = "id") int id) throws NotFoundException {
        logger.info("begin activate user");
        User user = us.findById(id);
        logger.info("User found: " + user.getId());
        user.setActive(true);
        us.activate(user);
        logger.info("User activated");
        logger.info("end activate user");

        return new ResponseEntity<>("User activated.", HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) throws NotFoundException {
        logger.info("Begin delete user");
        User user = us.findById(id);
        logger.info("User found: " + user.getId());
        us.deleteUser(user);
        logger.info("User deleted");
        logger.info("end delete user");

        return new ResponseEntity<>("User deleted", HttpStatus.OK);
    }

    @DeleteMapping("/users")
    public ResponseEntity<String> deleteAll() {
        us.deleteAll();
        return new ResponseEntity<>("All users deleted", HttpStatus.OK);
    }

    @ExceptionHandler(BadRequest.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(BadRequest br) {
        ErrorResponse errorResponse = new ErrorResponse("400", br.getMessage());
        logger.error(br.getMessage(), br);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException nfe) {
        ErrorResponse errorResponse = new ErrorResponse("404", nfe.getMessage());
        logger.error(nfe.getMessage(), nfe);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse("500", "Internal server error");
        logger.error(exception.getMessage(), exception);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
