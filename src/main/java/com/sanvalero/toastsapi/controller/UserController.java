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
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    final SimpleGrantedAuthority userAuthority = new SimpleGrantedAuthority("ROLE_USER");
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

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
        ModelMapper mapper = new ModelMapper();
        UserModel user = mapper.map(userDTO, UserModel.class);
        user.setBirthDate(LocalDate.parse(userDTO.getBirth_date(), formatter));
        if (user.getBirthDate() == null) {
            throw new BadRequestException("The birth date is incorrect or the format is not dd-MM-yyyy");
        }
        user.setPassword(UserModel.encoder().encode(userDTO.getPassword()));
        user.setCreationDate(LocalDate.now());
        user.setMoneySpent(0);
        user.setPublicationsNumber(0);
        return new ResponseEntity<>(us.addUser(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request)
            throws Exception {

        List<UserModel> user = us.findByUsername(request.getUsername());

        if (!user.isEmpty()) {
            if (UserModel.encoder().matches(request.getPassword(), user.get(0).getPassword())) {
                String token = jwtTokenProvider.createToken(user.get(0).getId(), request.getUsername());
                JwtResponse jwtResponse = new JwtResponse(request.getUsername(), token);
                return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
            } else {
                throw new BadRequestException(
                        "Credentials error, incorrect password for user " + request.getUsername());
            }
        } else {
            throw new BadRequestException("User not found with username: " + request.getUsername());
        }

    }

    @PatchMapping("/users/publications-number")
    public ResponseEntity<String> updatePublicationsNumber(@RequestParam(value = "id") int id)
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

    @PatchMapping("/users/money-spent")
    public ResponseEntity<String> updateMoneySpent(@RequestParam(value = "id") int id)
            throws NotFoundException {

        logger.info("begin update money spent");
        try {
            UserModel user = us.findById(id);
            logger.info("USer found: " + user.getId());
            user.setMoneySpent(us.sumPrice(id));
            us.updateMoneySpent(user);
            logger.info("User money spent updated");
            logger.info("end update money spent");

            return new ResponseEntity<>("Money spent updated.", HttpStatus.OK);
        } catch (NotFoundException e) {
            logger.error("User not found with id: " + id, e);
            throw new NotFoundException("User not found with id: " + id);
        }
    }

    @PatchMapping("/users/password")
    public ResponseEntity<String> updatePassword(@RequestParam(value = "id") int id,
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

    @PatchMapping("/users/disable")
    public ResponseEntity<String> disable(@RequestParam(value = "id") int id) throws NotFoundException {
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

    @PatchMapping("/users/activate")
    public ResponseEntity<String> activate(@RequestParam(value = "id") int id) throws NotFoundException {
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

    @DeleteMapping("/users")
    public ResponseEntity<String> deleteAll() {
        us.deleteAll();
        return new ResponseEntity<>("All users deleted", HttpStatus.OK);
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
