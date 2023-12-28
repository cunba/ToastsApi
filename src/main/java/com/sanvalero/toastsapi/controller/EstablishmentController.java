package com.sanvalero.toastsapi.controller;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import com.sanvalero.toastsapi.exception.BadRequestException;
import com.sanvalero.toastsapi.exception.ErrorResponse;
import com.sanvalero.toastsapi.exception.NotFoundException;
import com.sanvalero.toastsapi.model.Establishment;
import com.sanvalero.toastsapi.model.dto.EstablishmentDTO;
import com.sanvalero.toastsapi.service.EstablishmentService;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EstablishmentController {
    @Autowired
    private EstablishmentService es;

    private long dateFrom = 1640995200000L;
    private final Logger logger = LoggerFactory.getLogger(EstablishmentController.class);

    @GetMapping(value = "/establishments")
    public ResponseEntity<List<Establishment>> getAll() {
        return new ResponseEntity<>(es.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/establishments/{id}")
    public ResponseEntity<Establishment> getById(@PathVariable int id) throws NotFoundException {
        try {
            Establishment establishment = es.findById(id);
            return new ResponseEntity<>(establishment, HttpStatus.OK);
        } catch (NotFoundException nfe) {
            logger.error("Establishment not found exception with id " + id + ".", nfe);
            throw new NotFoundException("Establishment with ID " + id + " does not exists.");
        }
    }

    @GetMapping(value = "/establishments/name/{name}")
    public ResponseEntity<Establishment> getByName(@PathVariable String name) {
        return new ResponseEntity<>(es.findByName(name), HttpStatus.OK);
    }

    @GetMapping(value = "/establishments/date/{date}")
    public ResponseEntity<List<Establishment>> getByCreationDate(@PathVariable long date)
            throws BadRequestException {
        if (date < dateFrom) {
            logger.error("Establishment get by date error.", new BadRequestException());
            throw new BadRequestException(
                    "The date must be in timestamp and more than " + dateFrom + " (01-01-2022 00:00:00).");
        }

        Timestamp timestamp = new Timestamp(date);
        LocalDate creationDate = timestamp.toLocalDateTime().toLocalDate();
        return new ResponseEntity<>(es.findByCreationDate(creationDate), HttpStatus.OK);
    }

    @GetMapping(value = "/establishments/date/between")
    public ResponseEntity<List<Establishment>> getByCreationDateBetween(
            @RequestParam(value = "minDate") long minDate,
            @RequestParam(value = "maxDate") long maxDate) throws BadRequestException {

        if (minDate < dateFrom || maxDate < dateFrom) {
            logger.error("Establishment get by date between error.", new BadRequestException());
            throw new BadRequestException(
                    "The dates must be in timestamp and more than " + dateFrom + " (01-01-2022 00:00:00).");
        }

        Timestamp minTimestamp = new Timestamp(minDate);
        LocalDate minDateLocal = minTimestamp.toLocalDateTime().toLocalDate();
        Timestamp maxTimestamp = new Timestamp(maxDate);
        LocalDate maxDateLocal = maxTimestamp.toLocalDateTime().toLocalDate();

        LocalDate changerDate = LocalDate.now();
        if (minDateLocal.isAfter(maxDateLocal)) {
            changerDate = minDateLocal;
            minDateLocal = maxDateLocal;
            maxDateLocal = changerDate;
        }

        return new ResponseEntity<>(es.findByCreationDateBetween(minDateLocal, maxDateLocal), HttpStatus.OK);
    }

    @GetMapping(value = "/establishments/open/{open}")
    public ResponseEntity<List<Establishment>> getByOpen(@PathVariable boolean open) {
        return new ResponseEntity<>(es.findByOpen(open), HttpStatus.OK);
    }

    @GetMapping(value = "/establishments/score/{score}")
    public ResponseEntity<List<Establishment>> getByScore(@PathVariable float score)
            throws BadRequestException {
        if (score < 0 || score > 5) {
            logger.error("Establishment get by puntuation error.", new BadRequestException());
            throw new BadRequestException("The score must be between 0 and 5.");
        }
        return new ResponseEntity<>(es.findByScore(score), HttpStatus.OK);
    }

    @GetMapping(value = "/establishments/score/between")
    public ResponseEntity<List<Establishment>> getByScoreBetween(
            @RequestParam(value = "minScore") float minScore,
            @RequestParam(value = "maxScore") float maxScore) throws BadRequestException {

        if (minScore < 0 || minScore > 5 || maxScore < 0 || maxScore > 5) {
            logger.error("Establishment get by puntuation between error.", new BadRequestException());
            throw new BadRequestException("The score must be between 0 and 5.");
        }

        float templateScore = 0;
        if (minScore > maxScore) {
            templateScore = minScore;
            minScore = maxScore;
            maxScore = templateScore;
        }

        return new ResponseEntity<>(es.findByScoreBetween(minScore, maxScore), HttpStatus.OK);
    }

    @Secured({ "ROLE_USER", "ROLE_ADMIN" })
    @PostMapping(value = "/establishments")
    public ResponseEntity<Establishment> create(@RequestBody EstablishmentDTO establishmentDTO) {
        logger.info("begin create establishment");
        ModelMapper mapper = new ModelMapper();
        Establishment establishment = mapper.map(establishmentDTO, Establishment.class);
        // establishment.setLocation(establishmentDTO.getLocation().toString());
        establishment.setCreationDate(LocalDate.now());
        establishment.setScore(0);

        logger.info("Establishment mapped");
        Establishment toPrint = es.addEstablishment(establishment);
        logger.info("Establishment created");
        logger.info("end create establishment");
        return new ResponseEntity<>(toPrint, HttpStatus.CREATED);
    }

    @Secured({ "ROLE_USER", "ROLE_ADMIN" })
    @PutMapping(value = "/establishments/{id}")
    public ResponseEntity<Establishment> update(@RequestBody EstablishmentDTO establishmentDTO, @PathVariable int id)
            throws NotFoundException {

        logger.info("begin update establishment");
        try {
            Establishment establishmentToUpdate = es.findById(id);
            logger.info("Establishment found: " + establishmentToUpdate.getId());
            establishmentToUpdate.setLocation(establishmentDTO.getLocation());
            establishmentToUpdate.setName(establishmentDTO.getName());
            establishmentToUpdate.setOpen(establishmentDTO.isOpen());
            logger.info("Properties setted");
            Establishment toPrint = es.updateEstablishment(establishmentToUpdate);
            logger.info("Establishments updated");
            logger.info("end update establishment");

            return new ResponseEntity<>(toPrint, HttpStatus.OK);
        } catch (NotFoundException nfe) {
            logger.error("Establihsment not found exception with id " + id + ".", nfe);
            throw new NotFoundException("Establishment with ID " + id + " does not exists.");
        }

    }

    @Secured({ "ROLE_USER", "ROLE_ADMIN" })
    @PatchMapping(value = "/establishments/{id}/score")
    public ResponseEntity<String> updateScore(@PathVariable int id) throws NotFoundException {
        logger.info("begin update score");
        try {
            Establishment establishment = es.findById(id);
            logger.info("Establishment found: " + id);
            float score = es.sumScore(id) / es.countPublications(id);
            establishment.setScore(score);
            es.updateScore(establishment);
            logger.info("Establishment score updated");
            logger.info("end update score");

            return new ResponseEntity<>("Score updated.", HttpStatus.OK);
        } catch (NotFoundException nfe) {
            logger.error("Establihsment not found exception with id " + id + ".", nfe);
            throw new NotFoundException("Establishment with ID " + id + " does not exists.");
        } catch (Exception e) {
            // Quiere decir que no hay publicaciones para obtener el precio y actualizarlo
            return new ResponseEntity<>(
                    "Money spent can't be updated due to lack of publications for the establishment "
                            + id + ".",
                    HttpStatus.OK);
        }
    }

    @Secured({ "ROLE_ADMIN" })
    @DeleteMapping(value = "/establishments/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) throws NotFoundException {
        logger.info("begin delete establishment");
        try {
            Establishment establishment = es.findById(id);
            logger.info("Establishment found: " + establishment.getId());
            es.deleteEstablishment(establishment);
            logger.info("Establishment deleted");
            logger.info("end delete establishment");

            return new ResponseEntity<>("Establishment deleted.", HttpStatus.OK);
        } catch (NotFoundException nfe) {
            logger.error("Establihsment not found exception with id " + id + ".", nfe);
            throw new NotFoundException("Establishment with ID " + id + "does not exists.");
        }
    }

    @Secured({ "ROLE_USER", "ROLE_ADMIN" })
    @DeleteMapping(value = "/establishments")
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
