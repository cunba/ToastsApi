package com.sanvalero.toteco.controller.impl;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.validation.ConstraintViolationException;

import org.modelmapper.ModelMapper;
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

import com.sanvalero.toteco.controller.EstablishmentApi;
import com.sanvalero.toteco.exception.BadRequestException;
import com.sanvalero.toteco.exception.ErrorResponse;
import com.sanvalero.toteco.exception.NotFoundException;
import com.sanvalero.toteco.model.Establishment;
import com.sanvalero.toteco.model.dto.EstablishmentDTO;
import com.sanvalero.toteco.model.utils.HandledResponse;
import com.sanvalero.toteco.service.EstablishmentService;

@RestController
public class EstablishmentController implements EstablishmentApi {
    @Autowired
    private EstablishmentService es;

    private final Logger logger = LoggerFactory.getLogger(EstablishmentController.class);

    @Override
    public ResponseEntity<List<Establishment>> getAll() {
        return new ResponseEntity<>(es.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Establishment> getById(@PathVariable UUID id) throws NotFoundException {
        try {
            Establishment establishment = es.findById(id);
            return new ResponseEntity<>(establishment, HttpStatus.OK);
        } catch (NotFoundException nfe) {
            logger.error("Establishment not found exception with id " + id + ".", nfe);
            throw new NotFoundException("Establishment with ID " + id + " does not exists.");
        }
    }

    @Override
    public ResponseEntity<List<Establishment>> getByName(@PathVariable String name) {
        return new ResponseEntity<>(es.findByName(name), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Establishment> save(@RequestBody EstablishmentDTO establishmentDTO) {
        logger.info("begin create establishment");
        ModelMapper mapper = new ModelMapper();
        Establishment establishment = mapper.map(establishmentDTO, Establishment.class);
        // establishment.setLocation(establishmentDTO.getLocation().toString());
        establishment.setCreated(LocalDate.now().toEpochDay());
        establishment.setScore(0);

        logger.info("Establishment mapped");
        Establishment toPrint = es.save(establishment);
        logger.info("Establishment created");
        logger.info("end create establishment");
        return new ResponseEntity<>(toPrint, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<HandledResponse> update(@PathVariable UUID id, @RequestBody EstablishmentDTO establishmentDTO)
            throws NotFoundException {

        logger.info("begin update establishment");
        try {
            Establishment establishmentToUpdate = es.findById(id);
            logger.info("Establishment found: " + establishmentToUpdate.getId());
            establishmentToUpdate.setLocation(establishmentDTO.getLocation());
            establishmentToUpdate.setName(establishmentDTO.getName());
            establishmentToUpdate.setOpen(establishmentDTO.isOpen());
            logger.info("Properties setted");
            es.update(establishmentToUpdate);
            logger.info("Establishments updated");
            logger.info("end update establishment");

            return new ResponseEntity<>(new HandledResponse("Establishment updated", 1), HttpStatus.OK);
        } catch (NotFoundException nfe) {
            logger.error("Establihsment not found exception with id " + id + ".", nfe);
            throw new NotFoundException("Establishment with ID " + id + " does not exists.");
        }
    }

    @Override
    public ResponseEntity<Establishment> delete(@PathVariable UUID id) throws NotFoundException {
        logger.info("begin delete establishment");
        try {
            Establishment establishment = es.findById(id);
            logger.info("Establishment found: " + establishment.getId());
            es.delete(establishment);
            logger.info("Establishment deleted");
            logger.info("end delete establishment");

            return new ResponseEntity<>(establishment, HttpStatus.OK);
        } catch (NotFoundException nfe) {
            logger.error("Establihsment not found exception with id " + id + ".", nfe);
            throw new NotFoundException("Establishment with ID " + id + "does not exists.");
        }
    }

    @Override
    public ResponseEntity<String> deleteAll() {
        es.deleteAll();
        return new ResponseEntity<>("All establishments deleted.", HttpStatus.OK);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException br) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Bad request Exception");
        ErrorResponse errorResponse = new ErrorResponse("400", error, br.getMessage());
        logger.error(br.getMessage(), br);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException nfe) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Internal server error");
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
