package com.sanvalero.toastsapi.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.sanvalero.toastsapi.exception.ErrorResponse;
import com.sanvalero.toastsapi.exception.NotFoundException;
import com.sanvalero.toastsapi.model.Establishment;
import com.sanvalero.toastsapi.model.Publication;
import com.sanvalero.toastsapi.model.User;
import com.sanvalero.toastsapi.model.dto.PublicationDTO;
import com.sanvalero.toastsapi.service.EstablishmentService;
import com.sanvalero.toastsapi.service.PublicationService;
import com.sanvalero.toastsapi.service.UserService;

import org.modelmapper.ModelMapper;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublicationController {
    @Autowired
    private PublicationService ps;
    @Autowired
    private UserService us;
    @Autowired
    private EstablishmentService es;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @GetMapping("/publications")
    public List<Publication> getAll() {
        return ps.findAll();
    }

    @GetMapping("/publication/id={id}")
    public Publication getById(@PathVariable int id) throws NotFoundException {
        return ps.findById(id);
    }

    @GetMapping("/publications/date={dateString}")
    public List<Publication> getByDate(@PathVariable String dateString) {
        LocalDate date = LocalDate.parse(dateString, formatter);

        return ps.findByDate(date);
    }

    @GetMapping("/publications/minDate={minDateString}-maxDate={maxDateString}")
    public List<Publication> getByDateBetween(@PathVariable String minDateString,
            @PathVariable String maxDateString) {

        LocalDate minDate = LocalDate.parse(minDateString, formatter);
        LocalDate maxDate = LocalDate.parse(maxDateString, formatter);

        LocalDate changerDate = LocalDate.now();
        if (minDate.isAfter(maxDate)) {
            changerDate = minDate;
            minDate = maxDate;
            maxDate = changerDate;
        }

        return ps.findByDateBetween(minDate, maxDate);
    }

    @GetMapping("/publications/price={price}")
    public List<Publication> getByTotalPrice(@PathVariable float price) {
        return ps.findByTotalPrice(price);
    }

    @GetMapping("/publications/minPrice={minPrice}-maxPrice={maxPrice}")
    public List<Publication> getByPriceBetween(@PathVariable float minPrice,
            @PathVariable float maxPrice) {

        float templatePrice = 0;
        if (minPrice > maxPrice) {
            templatePrice = minPrice;
            minPrice = maxPrice;
            maxPrice = templatePrice;
        }

        return ps.findByTotalPriceBetween(minPrice, maxPrice);
    }

    @GetMapping("/publications/punctuation={punctuation}")
    public List<Publication> getByPunctuation(@PathVariable float punctuation) {
        return ps.findByTotalPunctuation(punctuation);
    }

    @GetMapping("/publications/minPunctuation={minPunctuation}-maxPunctuation={maxPunctuation}")
    public List<Publication> getByPunctuationBetween(@PathVariable float minPunctuation,
            @PathVariable float maxPunctuation) {

        float templatePunctuation = 0;
        if (minPunctuation > maxPunctuation) {
            templatePunctuation = minPunctuation;
            minPunctuation = maxPunctuation;
            maxPunctuation = templatePunctuation;
        }

        return ps.findByTotalPunctuationBetween(minPunctuation, maxPunctuation);
    }

    @GetMapping("/publications/establishment")
    public List<Publication> getByEstablishmentId(@RequestParam(value = "id") int id) throws NotFoundException {
        Establishment establishment = es.findById(id);

        return ps.findByEstablishment(establishment);
    }

    @GetMapping("/publications/user")
    public List<Publication> getByUserId(@RequestParam(value = "id") int id) throws NotFoundException {
        User user = us.findById(id);

        return ps.findByUser(user);
    }

    @PostMapping("/publication")
    public Publication create(@RequestBody PublicationDTO publicationDTO) throws NotFoundException {
        Establishment establishment = es.findById(publicationDTO.getEstablishmentId());
        User user = us.findById(publicationDTO.getUserId());

        ModelMapper mapper = new ModelMapper();
        Publication publication = mapper.map(publicationDTO, Publication.class);
        publication.setDate(LocalDate.now());
        publication.setEstablishment(establishment);
        publication.setUser(user);

        return ps.addPublication(publication);
    }

    @PutMapping("/publication/id={id}")
    public Publication update(@RequestBody PublicationDTO publicationDTO, @PathVariable int id)
            throws NotFoundException {

        Publication publication = ps.findById(id);

        Establishment establishment = es.findById(publicationDTO.getEstablishmentId());
        User user = us.findById(publicationDTO.getUserId());

        publication.setPhoto(publicationDTO.getPhoto());
        publication.setTotalPrice(publicationDTO.getTotalPrice());
        publication.setTotalPunctuation(publicationDTO.getTotalPunctuation());
        publication.setEstablishment(establishment);
        publication.setUser(user);

        return ps.modifyPublication(publication);
    }

    @DeleteMapping("/publication/id={id}")
    public Publication delete(@PathVariable int id) throws NotFoundException {
        Publication publication = ps.findById(id);

        return ps.deletePublication(publication);
    }

    @DeleteMapping("/publications")
    public String deleteAll() {
        ps.deleteAll();

        return "All publications deleted";
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException nfe) {
        ErrorResponse errorResponse = new ErrorResponse("404", nfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
