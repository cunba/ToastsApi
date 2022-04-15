package com.sanvalero.toastsapi.controller;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

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

    @GetMapping("/establishments")
    public ResponseEntity<List<Establishment>> getAll() {
        return new ResponseEntity<>(es.findAll(), HttpStatus.OK);
    }

    @GetMapping("/establishments/{id}")
    public ResponseEntity<Establishment> getById(@PathVariable int id) throws NotFoundException {
        try {
            es.findById(id);
            return new ResponseEntity<>(es.findById(id), HttpStatus.OK);
        } catch (NotFoundException nfe) {
            throw new NotFoundException("Stablishment with ID " + id + "does not exists.");
        }
    }

    @GetMapping("/establishments/name/{name}")
    public ResponseEntity<Establishment> getByName(@PathVariable String name) {
        return new ResponseEntity<>(es.findByName(name), HttpStatus.OK);
    }

    @GetMapping("/establishments/date/{creationDateTimestamp}")
    public ResponseEntity<List<Establishment>> getByCreationDate(@PathVariable long creationDateTimestamp)
            throws BadRequestException {
        if (creationDateTimestamp < dateFrom) {
            throw new BadRequestException("The date must be in timestamp and more than " + dateFrom + " (01-01-2022 00:00:00).");
        } else {
            Timestamp timestamp = new Timestamp(creationDateTimestamp);
            LocalDate creationDate = timestamp.toLocalDateTime().toLocalDate();
            return new ResponseEntity<>(es.findByCreationDate(creationDate), HttpStatus.OK);
        }
    }

    @GetMapping("/establishments/date/between")
    public ResponseEntity<List<Establishment>> getByCreationDateBetween(
            @PathVariable(value = "minDate") long minDateTimestamp,
            @PathVariable(value = "maxDate") long maxDateTimestamp) throws BadRequestException {

        if (minDateTimestamp < dateFrom || maxDateTimestamp < dateFrom) {
            throw new BadRequestException("The dates must be in timestamp and more than " + dateFrom + " (01-01-2022 00:00:00).");
        } else {
            Timestamp minTimestamp = new Timestamp(minDateTimestamp);
            LocalDate minDate = minTimestamp.toLocalDateTime().toLocalDate();
            Timestamp maxTimestamp = new Timestamp(maxDateTimestamp);
            LocalDate maxDate = maxTimestamp.toLocalDateTime().toLocalDate();

            LocalDate changerDate = LocalDate.now();
            if (minDate.isAfter(maxDate)) {
                changerDate = minDate;
                minDate = maxDate;
                maxDate = changerDate;
            }

            return new ResponseEntity<>(es.findByCreationDateBetween(minDate, maxDate), HttpStatus.OK);
        }
    }

    @GetMapping("/establishments/open/{open}")
    public ResponseEntity<List<Establishment>> getByOpen(@PathVariable boolean open) {
        return new ResponseEntity<>(es.findByOpen(open), HttpStatus.OK);
    }

    @GetMapping("/establishments/location/{location}")
    public ResponseEntity<List<Establishment>> getByLocation(@PathVariable String location) {
        return new ResponseEntity<>(es.findByLocation(location), HttpStatus.OK);
    }

    @GetMapping("/establishments/punctuation/{punctuation}")
    public ResponseEntity<List<Establishment>> getByPunctuation(@PathVariable float punctuation) {
        return new ResponseEntity<>(es.findByPunctuation(punctuation), HttpStatus.OK);
    }

    @GetMapping("/establishments/punctuation/between")
    public ResponseEntity<List<Establishment>> getByPunctuationBetween(
            @RequestParam(value = "minPunctuation") float minPunctuation,
            @RequestParam(value = "maxPunctuation") float maxPunctuation) {

        float templatePunctuation = 0;
        if (minPunctuation > maxPunctuation) {
            templatePunctuation = minPunctuation;
            minPunctuation = maxPunctuation;
            maxPunctuation = templatePunctuation;
        }

        return new ResponseEntity<>(es.findByPunctuationBetween(minPunctuation, maxPunctuation), HttpStatus.OK);
    }

    @PostMapping("/establishments")
    public ResponseEntity<Establishment> create(@RequestBody EstablishmentDTO establishmentDTO) {
        logger.info("begin create establishment");
        ModelMapper mapper = new ModelMapper();
        Establishment establishment = mapper.map(establishmentDTO, Establishment.class);
        establishment.setCreationDate(LocalDate.now());
        establishment.setPunctuation(0);

        logger.info("Establishment mapped");
        Establishment toPrint = es.addEstablishment(establishment);
        logger.info("Establishment created");
        logger.info("end create establishment");
        return new ResponseEntity<>(toPrint, HttpStatus.CREATED);
    }

    @PutMapping("/establishments/{id}")
    public ResponseEntity<Establishment> update(@RequestBody EstablishmentDTO establishmentDTO, @PathVariable int id)
            throws NotFoundException {

        logger.info("begin update establishment");
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
    }

    @PatchMapping("/establishments/punctuation")
    public ResponseEntity<String> updatePunctuation(@RequestParam(value = "id") int id) throws NotFoundException {

        logger.info("begin update punctuation");
        Establishment establishment = es.findById(id);
        logger.info("Establishment found: " + id);
        establishment.setPunctuation(es.sumPunctuation(id));
        es.updatePunctuation(establishment);
        logger.info("Establishment punctuation updated");
        logger.info("end update punctuation");

        return new ResponseEntity<>("Punctuation updated.", HttpStatus.OK);
    }

    @DeleteMapping("/establishments/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) throws NotFoundException {
        logger.info("begin delete establishment");
        Establishment establishment = es.findById(id);
        logger.info("Establishment found: " + establishment.getId());
        es.deleteEstablishment(establishment);
        logger.info("Establishment deleted");
        logger.info("end delete establishment");

        return new ResponseEntity<>("Establishment deleted.", HttpStatus.OK);
    }

    @DeleteMapping("/establishments/delete")
    public ResponseEntity<String> deleteAll() {
        es.deleteAll();

        return new ResponseEntity<>("All establishments deleted.", HttpStatus.OK);
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
