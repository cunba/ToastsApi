package com.sanvalero.toteco.controller.impl;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sanvalero.toteco.controller.PublicationApi;
import com.sanvalero.toteco.exception.BadRequestException;
import com.sanvalero.toteco.exception.ErrorResponse;
import com.sanvalero.toteco.exception.NotFoundException;
import com.sanvalero.toteco.model.Establishment;
import com.sanvalero.toteco.model.Publication;
import com.sanvalero.toteco.model.UserModel;
import com.sanvalero.toteco.model.dto.PublicationDTO;
import com.sanvalero.toteco.model.utils.HandledResponse;
import com.sanvalero.toteco.service.EstablishmentService;
import com.sanvalero.toteco.service.PublicationService;
import com.sanvalero.toteco.service.UserService;

@RestController
public class PublicationController implements PublicationApi {
    @Autowired
    private PublicationService ps;
    @Autowired
    private UserService us;
    @Autowired
    private EstablishmentService es;

    private final Logger logger = LoggerFactory.getLogger(PublicationController.class);

    @Override
    public ResponseEntity<List<Publication>> getAll() {
        return new ResponseEntity<>(ps.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Publication> getById(@PathVariable UUID id) throws NotFoundException {
        try {
            return new ResponseEntity<>(ps.findById(id), HttpStatus.OK);
        } catch (NotFoundException nfe) {
            logger.error("Publication not found exception with id " + id + ".", nfe);
            throw new NotFoundException("Publication with ID " + id + " does not exists.");
        }
    }

    @Override
    public ResponseEntity<List<Publication>> getByEstablishment(@PathVariable UUID id)
            throws NotFoundException {

        try {
            Establishment establishment = es.findById(id);
            return new ResponseEntity<>(ps.findByEstablishment(establishment), HttpStatus.OK);
        } catch (NotFoundException nfe) {
            logger.error("Establishment not found exception with id " + id + ".", nfe);
            throw new NotFoundException("Establishment with ID " + id + " does not exists.");
        }
    }

    @Override
    public ResponseEntity<List<Publication>> getByUser(@PathVariable UUID id) throws NotFoundException {
        try {
            UserModel user = us.findById(id);
            return new ResponseEntity<>(ps.findByUser(user), HttpStatus.OK);
        } catch (NotFoundException nfe) {
            logger.error("User not found exception with id " + id + ".", nfe);
            throw new NotFoundException("User with ID " + id + " does not exists.");
        }
    }

    @Override
    public ResponseEntity<Publication> save(@RequestBody PublicationDTO publicationDTO) throws NotFoundException {
        logger.info("begin create publication");

        Establishment establishment = null;
        try {
            establishment = es.findById(publicationDTO.getEstablishmentId());
        } catch (NotFoundException nfe) {
            logger.error("Establishment not found exception with id " + publicationDTO.getEstablishmentId() + ".", nfe);
            throw new NotFoundException(
                    "Establishment with ID " + publicationDTO.getEstablishmentId() + " does not exists.");
        }
        logger.info("Establishment found: " + establishment.getId());

        UserModel user = null;
        try {
            user = us.findById(publicationDTO.getUserId());
        } catch (NotFoundException nfe) {
            logger.error("User not found exception with id " + publicationDTO.getUserId() + ".", nfe);
            throw new NotFoundException("User with ID " + publicationDTO.getUserId() + " does not exists.");
        }
        logger.info("User found: " + user.getId());

        Publication publication = new Publication();
        publication.setImage(publicationDTO.getImage());
        publication.setTotalPrice(0);
        publication.setTotalScore(0);
        publication.setCreated(LocalDate.now().toEpochDay());
        publication.setEstablishment(establishment);
        publication.setUser(user);
        publication.setTotalPrice(publicationDTO.getTotalPrice());
        publication.setTotalScore(publicationDTO.getTotalScore());
        logger.info("Publication mapped");
        logger.info("end create publication");

        return new ResponseEntity<>(ps.save(publication), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<HandledResponse> update(@PathVariable UUID id, @RequestBody PublicationDTO publicationDTO)
            throws NotFoundException {

        logger.info("begin update publication");
        Publication publication = null;
        try {
            publication = ps.findById(id);
            logger.info("Publication found: " + publication.getId());
        } catch (NotFoundException nfe) {
            logger.error("Publication not found exception with id " + id + ".", nfe);
            throw new NotFoundException("Publication with ID " + id + " does not exists.");
        }

        Establishment establishment = null;
        try {
            establishment = es.findById(publicationDTO.getEstablishmentId());
        } catch (NotFoundException nfe) {
            logger.error("Establishment not found exception with id " + id + ".", nfe);
            throw new NotFoundException("Establishment with ID " + id + " does not exists.");
        }
        logger.info("Establishment found: " + establishment.getId());

        publication.setImage(publicationDTO.getImage());
        publication.setTotalPrice(publicationDTO.getTotalPrice());
        publication.setTotalScore(publicationDTO.getTotalScore());
        publication.setEstablishment(establishment);

        ps.update(publication);
        logger.info("Publication properties updated");
        logger.info("end update publication");

        return new ResponseEntity<>(new HandledResponse("Publication updated", 1), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Publication> delete(@PathVariable UUID id) throws NotFoundException {
        logger.info("begin delete publication");
        try {
            Publication publication = ps.findById(id);
            logger.info("Publication found:" + publication.getId());
            ps.delete(publication);
            logger.info("Publication deleted");

            return new ResponseEntity<>(publication, HttpStatus.OK);
        } catch (NotFoundException nfe) {
            logger.error("Publication not found exception with id " + id + ".", nfe);
            throw new NotFoundException("Publication with ID " + id + " does not exists.");
        }
    }

    @Override
    public ResponseEntity<String> deleteAll() {
        ps.deleteAll();

        return new ResponseEntity<>("All publications deleted", HttpStatus.OK);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException br) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Bad request exception");
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
