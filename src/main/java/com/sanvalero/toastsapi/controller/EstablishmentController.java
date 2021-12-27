package com.sanvalero.toastsapi.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.sanvalero.toastsapi.exception.ErrorResponse;
import com.sanvalero.toastsapi.exception.NotFoundException;
import com.sanvalero.toastsapi.model.Establishment;
import com.sanvalero.toastsapi.service.EstablishmentService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class EstablishmentController {
    @Autowired
    private EstablishmentService es;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private final Logger logger = LoggerFactory.getLogger(EstablishmentController.class);

    @GetMapping("/establishments")
    public ResponseEntity<List<Establishment>> getAll() {
        return new ResponseEntity<>(es.findAll(), HttpStatus.OK);
    }

    @GetMapping("/establishment/id/{id}")
    public ResponseEntity<Establishment> getById(@PathVariable int id) throws NotFoundException {
        return new ResponseEntity<>(es.findById(id), HttpStatus.OK);
    }

    @GetMapping("/establishment/name/{name}")
    public ResponseEntity<Establishment> getByName(@PathVariable String name) {
        return new ResponseEntity<>(es.findByName(name), HttpStatus.OK);
    }

    @GetMapping("/establishments/date/{creationDateString}")
    public ResponseEntity<List<Establishment>> getByCreationDate(@PathVariable String creationDateString) {
        LocalDate creationDate = LocalDate.parse(creationDateString, formatter);
        return new ResponseEntity<>(es.findByCreationDate(creationDate), HttpStatus.OK);
    }

    @GetMapping("/establishments/dates/{minDateString}/{maxDateString}")
    public ResponseEntity<List<Establishment>> getByCreationDateBetween(@PathVariable String minDateString,
            @PathVariable String maxDateString) {

        LocalDate minDate = LocalDate.parse(minDateString, formatter);
        LocalDate maxDate = LocalDate.parse(maxDateString, formatter);

        LocalDate changerDate = LocalDate.now();
        if (minDate.isAfter(maxDate)) {
            changerDate = minDate;
            minDate = maxDate;
            maxDate = changerDate;
        }

        return new ResponseEntity<>(es.findByCreationDateBetween(minDate, maxDate), HttpStatus.OK);
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

    @GetMapping("/establishments/punctuations/{minPunctuation}-{maxPunctuation}")
    public ResponseEntity<List<Establishment>> getByPunctuationBetween(@PathVariable float minPunctuation,
            @PathVariable float maxPunctuation) {

        float templatePunctuation = 0;
        if (minPunctuation > maxPunctuation) {
            templatePunctuation = minPunctuation;
            minPunctuation = maxPunctuation;
            maxPunctuation = templatePunctuation;
        }

        return new ResponseEntity<>(es.findByPunctuationBetween(minPunctuation, maxPunctuation), HttpStatus.OK);
    }

    @PostMapping("/establishment/create")
    public ResponseEntity<Establishment> create(@RequestBody Establishment establishment) {
        establishment.setCreationDate(LocalDate.now());
        return new ResponseEntity<>(es.addEstablishment(establishment), HttpStatus.OK);
    }

    @PutMapping("/establishment/update/{id}")
    public ResponseEntity<Establishment> update(@RequestBody Establishment establishment, @PathVariable int id)
            throws NotFoundException {

        logger.info("begin update establishment");
        Establishment establishmentToUpdate = es.findById(id);
        logger.info("Establishment found: " + establishment.getId());
        establishmentToUpdate.setCreationDate(establishment.getCreationDate());
        establishmentToUpdate.setLocation(establishment.getLocation());
        establishmentToUpdate.setName(establishment.getName());
        establishmentToUpdate.setOpen(establishment.isOpen());
        establishmentToUpdate.setPublications(establishment.getPublications());
        establishmentToUpdate.setPunctuation(establishment.getPunctuation());
        logger.info("Establishments properties updated");
        logger.info("end update establishment");

        return new ResponseEntity<>(es.updateEstablishment(establishmentToUpdate), HttpStatus.OK);
    }

    @DeleteMapping("/establishment/delete/{id}")
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

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException nfe) {
        ErrorResponse errorResponse = new ErrorResponse("404", nfe.getMessage());
        logger.error(nfe.getMessage(), nfe);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse("999", "Internal server error");
        logger.error(exception.getMessage(), exception);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
