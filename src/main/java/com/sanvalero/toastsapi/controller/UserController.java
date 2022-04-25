package com.sanvalero.toastsapi.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.sanvalero.toastsapi.exception.BadRequestException;
import com.sanvalero.toastsapi.exception.ErrorResponse;
import com.sanvalero.toastsapi.exception.NotFoundException;
import com.sanvalero.toastsapi.model.UserModel;
import com.sanvalero.toastsapi.model.dto.UserDTO;
import com.sanvalero.toastsapi.security.JwtRequest;
import com.sanvalero.toastsapi.security.JwtResponse;
import com.sanvalero.toastsapi.security.JwtTokenProvider;
import com.sanvalero.toastsapi.service.UserService;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.security.SignatureException;

@RestController
@ControllerAdvice
public class UserController {
    @Autowired
    private UserService us;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Secured("ROLE_ADMIN")
    @GetMapping("/users")
    public ResponseEntity<List<UserModel>> getAllUsers() {
        return new ResponseEntity<>(us.findAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserModel> getById(@PathVariable int id) throws NotFoundException {

        try {
            UserModel user = us.findById(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (NotFoundException e) {
            logger.error("User not found with id: " + id, e);
            throw new NotFoundException("Not found user with id: " + id);
        }
    }

    @PostMapping("/users")
    public ResponseEntity<UserModel> create(@RequestBody UserDTO userDTO) throws BadRequestException {
        List<UserModel> userList = us.findByUsername(userDTO.getUsername());
        if (!userList.isEmpty()) {
            logger.error("Username in use.", new BadRequestException());
            throw new BadRequestException("The user " + userDTO.getUsername() + " already exists.");
        }

        userList = us.findByEmail(userDTO.getEmail());
        if (!userList.isEmpty()) {
            logger.error("Email in use.", new BadRequestException());
            throw new BadRequestException("The email " + userDTO.getEmail() + " already exists.");
        }

        ModelMapper mapper = new ModelMapper();
        UserModel user = mapper.map(userDTO, UserModel.class);
        user.setBirthDate(LocalDate.parse(userDTO.getBirth_date(), formatter));
        if (user.getBirthDate() == null) {
            logger.error("Users birth date error.", new BadRequestException());
            throw new BadRequestException("The birth date is incorrect or the format is not dd-MM-yyyy");
        }

        user.setRole(userDTO.getRole().toUpperCase());
        user.setPassword(UserModel.encoder().encode(userDTO.getPassword()));
        user.setCreationDate(LocalDate.now());
        user.setMoneySpent(0);
        user.setPublicationsNumber(0);
        user.setActive(true);
        return new ResponseEntity<>(us.addUser(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request)
            throws Exception {

        List<UserModel> user = us.findByUsername(request.getUsername());

        if (user.isEmpty()) {
            logger.error("User " + request.getUsername() + " not found.", new BadRequestException());
            throw new BadRequestException("User not found with username: " + request.getUsername());
        }

        if (!(UserModel.encoder().matches(request.getPassword(), user.get(0).getPassword()))) {
            logger.error("Credentials error user " + request.getUsername() + ".", new BadRequestException());
            throw new BadRequestException("Credentials error, incorrect password for user " + request.getUsername());
        }

        String token = jwtTokenProvider.createToken(user.get(0).getId(), request.getUsername(), user.get(0).getRole());
        JwtResponse jwtResponse = new JwtResponse(token);
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }

    @PatchMapping("/users/{id}/publications-number")
    public ResponseEntity<String> updatePublicationsNumber(@PathVariable int id)
            throws NotFoundException {

        logger.info("begin update publications number");
        try {
            UserModel user = us.findById(id);
            logger.info("User found: " + user.getId());
            user.setPublicationsNumber(us.countPublications(id));
            us.updatePublicationsNumber(user);
            logger.info("User publication number updated");
            logger.info("end update publications number");
            return new ResponseEntity<>("Publications number updated.", HttpStatus.OK);
        } catch (NotFoundException e) {
            logger.error("User not found with id: " + id, e);
            throw new NotFoundException("User not found with id: " + id);
        }

    }

    @PatchMapping("/users/{id}/money-spent")
    public ResponseEntity<String> updateMoneySpent(@PathVariable int id)
            throws NotFoundException {

        logger.info("begin update money spent");
        try {
            UserModel user = us.findById(id);
            logger.info("User found: " + user.getId());

            try {
                float price = us.sumPrice(id);
                user.setMoneySpent(price);
            } catch (Exception e) {
                // Quiere decir que no hay publicaciones para obtener el precio y actualizarlo
                return new ResponseEntity<>("Money spent can't be updated due to lack of publications for the user "
                        + user.getUsername() + ".", HttpStatus.OK);
            }

            us.updateMoneySpent(user);
            logger.info("User money spent updated");
            logger.info("end update money spent");

            return new ResponseEntity<>("Money spent updated.", HttpStatus.OK);
        } catch (NotFoundException e) {
            logger.error("User not found with id: " + id, e);
            throw new NotFoundException("User not found with id: " + id);
        }
    }

    @PatchMapping("/users/{id}/password")
    public ResponseEntity<String> updatePassword(@PathVariable int id,
            @RequestParam String password) throws NotFoundException {

        logger.info("begin update password");

        try {
            UserModel user = us.findById(id);
            logger.info("User found: " + user.getId());
            user.setPassword(UserModel.encoder().encode(password));
            us.updatePassword(user);
            logger.info("User password updated");
            logger.info("end update password");

            return new ResponseEntity<>("Password updated.", HttpStatus.OK);

        } catch (NotFoundException e) {
            logger.error("User not found with id: " + id, e);
            throw new NotFoundException("User not found with id: " + id);
        }
    }

    @PatchMapping("/users/{id}/disable")
    public ResponseEntity<String> disable(@PathVariable int id) throws NotFoundException {
        logger.info("begin disable user");

        try {
            UserModel user = us.findById(id);
            logger.info("User found: " + user.getId());
            user.setActive(false);
            us.disable(user);
            logger.info("User disabled");
            logger.info("end disable user");

            return new ResponseEntity<>("User disabled.", HttpStatus.OK);
        } catch (NotFoundException e) {
            logger.error("User not found with id: " + id, e);
            throw new NotFoundException("User not found with id: " + id);
        }
    }

    @PatchMapping("/users/{id}/activate")
    public ResponseEntity<String> activate(@PathVariable int id) throws NotFoundException {
        logger.info("begin activate user");

        try {
            UserModel user = us.findById(id);
            logger.info("User found: " + user.getId());
            user.setActive(true);
            us.activate(user);
            logger.info("User activated");
            logger.info("end activate user");

            return new ResponseEntity<>("User activated.", HttpStatus.OK);
        } catch (NotFoundException e) {
            logger.error("User not found with id: " + id, e);
            throw new NotFoundException("User not found with id: " + id);
        }
    }

    @Secured({ "ROLE_ADMIN" })
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) throws NotFoundException {
        logger.info("Begin delete user");

        try {
            UserModel user = us.findById(id);
            logger.info("User found: " + user.getId());
            us.deleteUser(user);
            logger.info("User deleted");
            logger.info("end delete user");

            return new ResponseEntity<>("User deleted", HttpStatus.OK);
        } catch (NotFoundException e) {
            logger.error("User not found with id: " + id, e);
            throw new NotFoundException("User not found with id: " + id);
        }
    }

    @Secured({ "ROLE_ADMIN" })
    @DeleteMapping("/users")
    public ResponseEntity<String> deleteAll() {
        us.deleteAll();
        return new ResponseEntity<>("All users deleted", HttpStatus.OK);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse("401", "Acceso denegado",
                "Este usuario no tiene permisos suficientes para realizar esta operación.");
        logger.error(e.getMessage(), e);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ErrorResponse> handleSignatureException(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse("401", "Acceso denegado", "Token caducado o en mal estado.");
        logger.error(e.getMessage(), e);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException br) {
        ErrorResponse errorResponse = new ErrorResponse("400", "Bad request exception", br.getMessage());
        logger.error(br.getMessage(), br);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException br) {
        ErrorResponse errorResponse = new ErrorResponse("400", "Bad request exception", br.getMessage());
        logger.error(br.getMessage(), br);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException nfe) {
        ErrorResponse errorResponse = new ErrorResponse("404", "Not found exception", nfe.getMessage());
        logger.error(nfe.getMessage(), nfe);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse("500", "Internal server error", exception.getMessage());
        logger.error(exception.getMessage(), exception);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
