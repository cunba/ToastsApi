package com.sanvalero.toastsapi.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.sanvalero.toastsapi.exception.ErrorResponse;
import com.sanvalero.toastsapi.exception.NotFoundException;
import com.sanvalero.toastsapi.model.Establishment;
import com.sanvalero.toastsapi.service.EstablishmentService;

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

    @GetMapping("/establishments")
    public List<Establishment> getAll() {
        return es.findAll();
    }

    @GetMapping("/establishment/id={id}")
    public Establishment getById(@PathVariable int id) throws NotFoundException {
        return es.findById(id);
    }

    @GetMapping("/establishment/name={name}")
    public Establishment getByName(@PathVariable String name) {
        return es.findByName(name);
    }

    @GetMapping("/establishments/date={creationDateString}")
    public List<Establishment> getByCreationDate(@PathVariable String creationDateString) {
        LocalDate creationDate = LocalDate.parse(creationDateString, formatter);
        return es.findByCreationDate(creationDate);
    }

    @GetMapping("/establishments/minDate={minDateString}-maxDate={maxDateString}")
    public List<Establishment> getByCreationDateBetween(@PathVariable String minDateString,
            @PathVariable String maxDateString) {
        LocalDate minDate = LocalDate.parse(minDateString, formatter);
        LocalDate maxDate = LocalDate.parse(maxDateString, formatter);
        return es.findByCreationDateBetween(minDate, maxDate);
    }

    @GetMapping("/establishments/open={open}")
    public List<Establishment> getByOpen(@PathVariable boolean open) {
        return es.findByOpen(open);
    }

    @GetMapping("/establishments/location={location}")
    public List<Establishment> getByLocation(@PathVariable String location) {
        return es.findByLocation(location);
    }

    @GetMapping("/establishments/punctuation={punctuation}")
    public List<Establishment> getByPunctuation(@PathVariable float punctuation) {
        return es.findByPunctuation(punctuation);
    }

    @GetMapping("/establishments/minPunctuation={minPunctuation}-maxPunctuation={maxPunctuation}")
    public List<Establishment> getByPunctuationBetween(@PathVariable float minPunctuation,
            @PathVariable float maxPunctuation) {

        return es.findByPunctuationBetween(minPunctuation, maxPunctuation);
    }

    @PostMapping("/establishment")
    public Establishment create(@RequestBody Establishment establishment) {
        return es.addEstablishment(establishment);
    }

    @PutMapping("/establishment/id={id}")
    public Establishment modify(@RequestBody Establishment establishment, @PathVariable int id)
            throws NotFoundException {
        Establishment establishmentToModify = es.findById(id);
        establishmentToModify.setCreationDate(establishment.getCreationDate());
        establishmentToModify.setLocation(establishment.getLocation());
        establishmentToModify.setName(establishment.getName());
        establishmentToModify.setOpen(establishment.isOpen());
        establishmentToModify.setPublications(establishment.getPublications());
        establishmentToModify.setPunctuation(establishment.getPunctuation());

        return es.modifyEstablishment(establishmentToModify);
    }

    @DeleteMapping("/establishment/id={id}")
    public Establishment delete(@PathVariable int id) throws NotFoundException {
        Establishment establishment = es.findById(id);
        es.deleteEstablishment(establishment);
        return establishment;
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException bnfe) {
        ErrorResponse errorResponse = new ErrorResponse("404", bnfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
