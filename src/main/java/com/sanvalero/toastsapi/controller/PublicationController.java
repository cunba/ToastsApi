package com.sanvalero.toastsapi.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.sanvalero.toastsapi.exception.ErrorResponse;
import com.sanvalero.toastsapi.exception.NotFoundException;
import com.sanvalero.toastsapi.model.Establishment;
import com.sanvalero.toastsapi.model.Publication;
import com.sanvalero.toastsapi.model.User;
import com.sanvalero.toastsapi.model.dto.PublicationBetweenDTO;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    public ResponseEntity<List<Publication>> getAll() {
        return new ResponseEntity<>(ps.findAll(), HttpStatus.OK);
    }

    @GetMapping("/publication/{id}")
    public ResponseEntity<Publication> getById(@PathVariable int id) throws NotFoundException {
        return new ResponseEntity<>(ps.findById(id), HttpStatus.OK);
    }

    @GetMapping("/publications/{dateString}")
    public ResponseEntity<List<Publication>> getByDate(@PathVariable String dateString) {
        LocalDate date = LocalDate.parse(dateString, formatter);

        return new ResponseEntity<>(ps.findByDate(date), HttpStatus.OK);
    }

    @GetMapping("/publications/{minDateString}-{maxDateString}")
    public ResponseEntity<List<Publication>> getByDateBetween(@PathVariable String minDateString,
            @PathVariable String maxDateString) {

        LocalDate minDate = LocalDate.parse(minDateString, formatter);
        LocalDate maxDate = LocalDate.parse(maxDateString, formatter);

        LocalDate changerDate = LocalDate.now();
        if (minDate.isAfter(maxDate)) {
            changerDate = minDate;
            minDate = maxDate;
            maxDate = changerDate;
        }

        return new ResponseEntity<>(ps.findByDateBetween(minDate, maxDate), HttpStatus.OK);
    }

    @GetMapping("/publications/{price}")
    public ResponseEntity<List<Publication>> getByTotalPrice(@PathVariable float price) {
        return new ResponseEntity<>(ps.findByTotalPrice(price), HttpStatus.OK);
    }

    @GetMapping("/publications/{minPrice}-{maxPrice}")
    public ResponseEntity<List<Publication>> getByPriceBetween(@PathVariable float minPrice,
            @PathVariable float maxPrice) {

        float templatePrice = 0;
        if (minPrice > maxPrice) {
            templatePrice = minPrice;
            minPrice = maxPrice;
            maxPrice = templatePrice;
        }

        return new ResponseEntity<>(ps.findByTotalPriceBetween(minPrice, maxPrice), HttpStatus.OK);
    }

    @GetMapping("/publications/{punctuation}")
    public ResponseEntity<List<Publication>> getByPunctuation(@PathVariable float punctuation) {
        return new ResponseEntity<>(ps.findByTotalPunctuation(punctuation), HttpStatus.OK);
    }

    @GetMapping("/publications/{minPunctuation}-{maxPunctuation}")
    public ResponseEntity<List<Publication>> getByPunctuationBetween(@PathVariable float minPunctuation,
            @PathVariable float maxPunctuation) {

        float templatePunctuation = 0;
        if (minPunctuation > maxPunctuation) {
            templatePunctuation = minPunctuation;
            minPunctuation = maxPunctuation;
            maxPunctuation = templatePunctuation;
        }

        return new ResponseEntity<>(ps.findByTotalPunctuationBetween(minPunctuation, maxPunctuation), HttpStatus.OK);
    }

    @GetMapping("/publications/establishment")
    public ResponseEntity<List<Publication>> getByEstablishmentId(@RequestParam(value = "id") int id)
            throws NotFoundException {
        Establishment establishment = es.findById(id);

        return new ResponseEntity<>(ps.findByEstablishment(establishment), HttpStatus.OK);
    }

    @GetMapping("/publications/user")
    public ResponseEntity<List<Publication>> getByUserId(@RequestParam(value = "id") int id) throws NotFoundException {
        User user = us.findById(id);

        return new ResponseEntity<>(ps.findByUser(user), HttpStatus.OK);
    }

    @GetMapping("/publications/product-")
    public ResponseEntity<List<Publication>> getByProductType(@RequestParam(value = "type") String productType) {
        return new ResponseEntity<>(ps.findByProductType(productType), HttpStatus.OK);
    }

    @GetMapping("/publications/")
    public ResponseEntity<List<Publication>> getByDateBetweenTotalPriceBetweenTotalPunctuationBetween(
            @RequestBody PublicationBetweenDTO pbDTO) {

        List<Publication> publications = ps.findByDateBetweenAndTotalPriceBetweenAndTotalPunctuationBetween(
                pbDTO.getMinDate(), pbDTO.getMaxDate(), pbDTO.getMinPrice(), pbDTO.getMaxPrice(),
                pbDTO.getMinPunctuation(), pbDTO.getMaxPunctuation());

        return new ResponseEntity<>(publications, HttpStatus.OK);
    }

    @PostMapping("/publication")
    public ResponseEntity<Publication> create(@RequestBody PublicationDTO publicationDTO) throws NotFoundException {
        Establishment establishment = es.findById(publicationDTO.getEstablishmentId());
        User user = us.findById(publicationDTO.getUserId());

        ModelMapper mapper = new ModelMapper();
        Publication publication = mapper.map(publicationDTO, Publication.class);
        publication.setTotalPrice(0);
        publication.setTotalPunctuation(0);
        publication.setDate(LocalDate.now());
        publication.setEstablishment(establishment);
        publication.setUser(user);

        return new ResponseEntity<>(ps.addPublication(publication), HttpStatus.OK);
    }

    @PatchMapping("/publication/update")
    public ResponseEntity<Publication> update(@RequestBody PublicationDTO publicationDTO,
            @RequestParam(value = "id") int id) throws NotFoundException {

        Publication publication = ps.findById(id);

        Establishment establishment = es.findById(publicationDTO.getEstablishmentId());

        publication.setPhoto(publicationDTO.getPhoto());
        publication.setTotalPrice(ps.totalPrice(publication.getId()));
        publication.setTotalPunctuation(ps.totalPunctuation(publication.getId()));
        publication.setEstablishment(establishment);

        return new ResponseEntity<>(ps.updatePublication(publication), HttpStatus.OK);
    }

    @PatchMapping("/publication/update-price-punctuation")
    public ResponseEntity<String> totalPricePunctuation(@RequestParam(value = "id") int id) throws NotFoundException {
        Publication publication = ps.findById(id);
        publication.setTotalPrice(ps.totalPrice(id));
        publication.setTotalPunctuation(ps.totalPunctuation(id));
        ps.updatePricePunctuation(publication);

        return new ResponseEntity<>("Precio y puntuación modificados.", HttpStatus.OK);
    }

    @DeleteMapping("/publication/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) throws NotFoundException {
        Publication publication = ps.findById(id);
        ps.deletePublication(publication);

        return new ResponseEntity<>("Publication deleted.", HttpStatus.OK);
    }

    @DeleteMapping("/publications")
    public ResponseEntity<String> deleteAll() {
        ps.deleteAll();

        return new ResponseEntity<>("All publications deleted", HttpStatus.OK);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException nfe) {
        ErrorResponse errorResponse = new ErrorResponse("404", nfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
