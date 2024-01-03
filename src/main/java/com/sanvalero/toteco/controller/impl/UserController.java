package com.sanvalero.toteco.controller.impl;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sanvalero.toteco.controller.UserApi;
import com.sanvalero.toteco.exception.BadRequestException;
import com.sanvalero.toteco.exception.ErrorResponse;
import com.sanvalero.toteco.exception.NotFoundException;
import com.sanvalero.toteco.model.UserModel;
import com.sanvalero.toteco.model.dto.PasswordChangeDTO;
import com.sanvalero.toteco.model.dto.UserDTO;
import com.sanvalero.toteco.model.utils.HandledResponse;
import com.sanvalero.toteco.security.JwtRequest;
import com.sanvalero.toteco.security.JwtResponse;
import com.sanvalero.toteco.security.JwtTokenProvider;
import com.sanvalero.toteco.service.UserService;

import io.jsonwebtoken.security.SignatureException;

@RestController
@ControllerAdvice
public class UserController implements UserApi {
    @Autowired
    private UserService us;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Override
    public ResponseEntity<List<UserModel>> getAll() {
        return new ResponseEntity<>(us.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserModel> getById(@PathVariable UUID id) throws NotFoundException {
        try {
            UserModel user = us.findById(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (NotFoundException e) {
            logger.error("User not found with id: " + id, e);
            throw new NotFoundException("Not found user with id: " + id);
        }
    }

    @Override
    public ResponseEntity<List<UserModel>> getByUsername(String username) {
        return new ResponseEntity<>(us.findByUsername(username), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<UserModel>> getByEmail(String email) {
        return new ResponseEntity<>(us.findByEmail(email), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Integer> getRecoveryCode(UUID id) {
        return new ResponseEntity<>(us.findRecoveryCode(id), HttpStatus.OK);
    }

    @Override
    @Produces(MediaType.TEXT_PLAIN)
    public ResponseEntity<UserModel> getUserLogged(@Context HttpServletRequest headers) throws NotFoundException {
        String token = jwtTokenProvider.resolveToken(headers);
        UUID id = UUID.fromString(jwtTokenProvider.getId(token));
        UserModel user = us.findById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserModel> save(@RequestBody UserDTO userDTO) throws BadRequestException {
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
        user.setBirthDate(userDTO.getBirthDate());
        user.setRole(userDTO.getRole().toUpperCase());
        user.setPassword(UserModel.encoder().encode(userDTO.getPassword()));
        user.setCreated(LocalDate.now().toEpochDay());
        user.setMoneySpent(0);
        user.setPublicationsNumber(0);
        user.setActive(true);
        return new ResponseEntity<>(us.save(user), HttpStatus.CREATED);
    }

    @Override
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

    @Override
    public ResponseEntity<HandledResponse> update(UUID id, UserDTO userDTO) throws NotFoundException {
        logger.info("begin update user");
        try {
            UserModel userToUpdate = us.findById(id);
            logger.info("User found: " + userToUpdate.getId());
            userToUpdate.setBirthDate(userDTO.getBirthDate());
            userToUpdate.setEmail(userDTO.getEmail());
            userToUpdate.setName(userDTO.getName());
            userToUpdate.setModified(LocalDate.now().toEpochDay());
            userToUpdate.setSurname(userDTO.getSurname());
            userToUpdate.setUsername(userDTO.getUsername());
            logger.info("Properties setted");
            us.update(userToUpdate);
            logger.info("Establishments updated");
            logger.info("end update establishment");

            return new ResponseEntity<>(new HandledResponse("User updated", 1), HttpStatus.OK);
        } catch (NotFoundException nfe) {
            logger.error("Establihsment not found exception with id " + id + ".", nfe);
            throw new NotFoundException("Establishment with ID " + id + " does not exists.");
        }
    }

    @Override
    public ResponseEntity<String> updatePublicationsNumber(@PathVariable UUID id)
            throws NotFoundException {

        logger.info("begin update publications number");
        try {
            UserModel user = us.findById(id);
            logger.info("User found: " + user.getId());
            user.setPublicationsNumber(us.countPublications(id));
            us.update(user);
            logger.info("User publication number updated");
            logger.info("end update publications number");
            return new ResponseEntity<>("Publications number updated.", HttpStatus.OK);
        } catch (NotFoundException e) {
            logger.error("User not found with id: " + id, e);
            throw new NotFoundException("User not found with id: " + id);
        }

    }

    @Override
    public ResponseEntity<String> updateMoneySpent(@PathVariable UUID id)
            throws NotFoundException {

        logger.info("begin update money spent");
        try {
            UserModel user = us.findById(id);
            logger.info("User found: " + user.getId());

            try {
                float price = us.sumMoney(id);
                user.setMoneySpent(price);
            } catch (Exception e) {
                // Quiere decir que no hay publicaciones para obtener el precio y actualizarlo
                return new ResponseEntity<>("Money spent can't be updated due to lack of publications for the user "
                        + user.getUsername() + ".", HttpStatus.OK);
            }

            us.update(user);
            logger.info("User money spent updated");
            logger.info("end update money spent");

            return new ResponseEntity<>("Money spent updated.", HttpStatus.OK);
        } catch (NotFoundException e) {
            logger.error("User not found with id: " + id, e);
            throw new NotFoundException("User not found with id: " + id);
        }
    }

    @Override
    public ResponseEntity<String> updatePassword(@PathVariable UUID id,
            @RequestBody PasswordChangeDTO password) throws NotFoundException {

        logger.info("begin update password");

        try {
            UserModel user = us.findById(id);
            logger.info("User found: " + user.getId());
            user.setPassword(UserModel.encoder().encode(password.getPassword()));
            us.update(user);
            logger.info("User password updated");
            logger.info("end update password");

            return new ResponseEntity<>("Password updated.", HttpStatus.OK);

        } catch (NotFoundException e) {
            logger.error("User not found with id: " + id, e);
            throw new NotFoundException("User not found with id: " + id);
        }
    }

    @Override
    public ResponseEntity<String> disable(@PathVariable UUID id) throws NotFoundException {
        logger.info("begin disable user");

        try {
            UserModel user = us.findById(id);
            logger.info("User found: " + user.getId());
            user.setActive(false);
            us.update(user);
            logger.info("User disabled");
            logger.info("end disable user");

            return new ResponseEntity<>("User disabled.", HttpStatus.OK);
        } catch (NotFoundException e) {
            logger.error("User not found with id: " + id, e);
            throw new NotFoundException("User not found with id: " + id);
        }
    }

    @Override
    public ResponseEntity<String> activate(@PathVariable UUID id) throws NotFoundException {
        logger.info("begin activate user");

        try {
            UserModel user = us.findById(id);
            logger.info("User found: " + user.getId());
            user.setActive(true);
            us.update(user);
            logger.info("User activated");
            logger.info("end activate user");

            return new ResponseEntity<>("User activated.", HttpStatus.OK);
        } catch (NotFoundException e) {
            logger.error("User not found with id: " + id, e);
            throw new NotFoundException("User not found with id: " + id);
        }
    }

    @Override
    public ResponseEntity<UserModel> delete(@PathVariable UUID id) throws NotFoundException {
        logger.info("Begin delete user");

        try {
            UserModel user = us.findById(id);
            logger.info("User found: " + user.getId());
            us.delete(user);
            logger.info("User deleted");
            logger.info("end delete user");

            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (NotFoundException e) {
            logger.error("User not found with id: " + id, e);
            throw new NotFoundException("User not found with id: " + id);
        }
    }

    @Override
    public ResponseEntity<String> deleteAll() {
        us.deleteAll();
        return new ResponseEntity<>("All users deleted", HttpStatus.OK);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(Exception e) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Acceso denegado");
        ErrorResponse errorResponse = new ErrorResponse("401", error,
                "Este usuario no tiene permisos suficientes para realizar esta operación.");
        logger.error(e.getMessage(), e);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ErrorResponse> handleSignatureException(Exception e) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Acceso denegado");
        ErrorResponse errorResponse = new ErrorResponse("401", error, "Token caducado o en mal estado.");
        logger.error(e.getMessage(), e);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException br) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Bad request exception");
        ErrorResponse errorResponse = new ErrorResponse("400", error, br.getMessage());
        logger.error(br.getMessage(), br);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException br) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Bad request expcetion");
        ErrorResponse errorResponse = new ErrorResponse("400", error, br.getMessage());
        logger.error(br.getMessage(), br);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException nfe) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Not Found Exception");
        ErrorResponse errorResponse = new ErrorResponse("404", error, nfe.getMessage());
        logger.error(nfe.getMessage(), nfe);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleArgumentNotValidException(MethodArgumentNotValidException manve) {
        Map<String, String> errors = new HashMap<>();
        manve.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        ErrorResponse errorResponse = new ErrorResponse("400", errors, "Validation error");
        logger.error(manve.getMessage(), manve);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException cve) {
        Map<String, String> errors = new HashMap<>();
        cve.getConstraintViolations().forEach(error -> {
            String fieldName = error.getPropertyPath().toString();
            String message = error.getMessage();
            errors.put(fieldName, message);
        });
        ErrorResponse errorResponse = new ErrorResponse("400", errors, "Validation error");
        logger.error(cve.getMessage(), cve);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Internal server error");
        ErrorResponse errorResponse = new ErrorResponse("500", error, exception.getMessage());
        logger.error(exception.getMessage(), exception);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
