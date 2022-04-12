package com.sanvalero.toastsapi.controller;

import java.sql.Timestamp;
import java.time.LocalDate;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

@RestController
public class PublicationController {
    @Autowired
    private PublicationService ps;
    @Autowired
    private UserService us;
    @Autowired
    private EstablishmentService es;

    private final Logger logger = LoggerFactory.getLogger(PublicationController.class);

    @GetMapping("/publications")
    public ResponseEntity<List<Publication>> getAll() {
        return new ResponseEntity<>(ps.findAll(), HttpStatus.OK);
    }

    @GetMapping("/publications/{id}")
    public ResponseEntity<Publication> getById(@PathVariable int id) throws NotFoundException {
        return new ResponseEntity<>(ps.findById(id), HttpStatus.OK);
    }

    @GetMapping("/publications/date/{dateTimestamp}")
    public ResponseEntity<List<Publication>> getByDate(@PathVariable long dateTimestamp) {
        Timestamp timestamp = new Timestamp(dateTimestamp);
        LocalDate date = timestamp.toLocalDateTime().toLocalDate();

        return new ResponseEntity<>(ps.findByDate(date), HttpStatus.OK);
    }

    @GetMapping("/publications/date/between/")
    public ResponseEntity<List<Publication>> getByDateBetween(@RequestParam(value = "minDate") long minDateTimestamp,
            @RequestParam(value = "maxDate") long maxDateTimestamp) {

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

        return new ResponseEntity<>(ps.findByDateBetween(minDate, maxDate), HttpStatus.OK);
    }

    @GetMapping("/publications/price/{price}")
    public ResponseEntity<List<Publication>> getByTotalPrice(@PathVariable float price) {
        return new ResponseEntity<>(ps.findByTotalPrice(price), HttpStatus.OK);
    }

    @GetMapping("/publications/price/betweem")
    public ResponseEntity<List<Publication>> getByPriceBetween(@RequestParam(value = "minPrice") float minPrice,
            @RequestParam(value = "maxPrice") float maxPrice) {

        float templatePrice = 0;
        if (minPrice > maxPrice) {
            templatePrice = minPrice;
            minPrice = maxPrice;
            maxPrice = templatePrice;
        }

        return new ResponseEntity<>(ps.findByTotalPriceBetween(minPrice, maxPrice), HttpStatus.OK);
    }

    @GetMapping("/publications/punctuation/{punctuation}")
    public ResponseEntity<List<Publication>> getByPunctuation(@PathVariable float punctuation) {
        return new ResponseEntity<>(ps.findByTotalPunctuation(punctuation), HttpStatus.OK);
    }

    @GetMapping("/publications/punctuation/between/")
    public ResponseEntity<List<Publication>> getByPunctuationBetween(@RequestParam(value = "minPunctuation") float minPunctuation,
            @RequestParam(value = "maxPunctuation") float maxPunctuation) {

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

    @GetMapping("/publications/product/")
    public ResponseEntity<List<Publication>> getByProductType(@RequestParam(value = "type") String productType) {
        return new ResponseEntity<>(ps.findByProductType(productType), HttpStatus.OK);
    }

    @GetMapping("/publications/date-price-punctuation/between/")
    public ResponseEntity<List<Publication>> getByDateBetweenTotalPriceBetweenTotalPunctuationBetween(
            @RequestBody PublicationBetweenDTO pbDTO) {

        List<Publication> publications = ps.findByDateBetweenAndTotalPriceBetweenAndTotalPunctuationBetween(
                pbDTO.getMinDate(), pbDTO.getMaxDate(), pbDTO.getMinPrice(), pbDTO.getMaxPrice(),
                pbDTO.getMinPunctuation(), pbDTO.getMaxPunctuation());

        return new ResponseEntity<>(publications, HttpStatus.OK);
    }

    @PostMapping("/publications")
    public ResponseEntity<Publication> create(@RequestBody PublicationDTO publicationDTO) throws NotFoundException {
        logger.info("begin create publication");
        Establishment establishment = es.findById(publicationDTO.getEstablishmentId());
        User user = us.findById(publicationDTO.getUserId());
        logger.info("Establishment found: " + establishment.getId());
        logger.info("User found: " + user.getId());

        ModelMapper mapper = new ModelMapper();
        Publication publication = mapper.map(publicationDTO, Publication.class);
        publication.setTotalPrice(0);
        publication.setTotalPunctuation(0);
        publication.setDate(LocalDate.now());
        publication.setEstablishment(establishment);
        publication.setUser(user);
        logger.info("Publication mapped");
        logger.info("end create publication");

        return new ResponseEntity<>(ps.addPublication(publication), HttpStatus.OK);
    }

    @PatchMapping("/publications")
    public ResponseEntity<Publication> update(@RequestBody PublicationDTO publicationDTO,
            @RequestParam(value = "id") int id) throws NotFoundException {

        logger.info("begin update publication");
        Publication publication = ps.findById(id);
        logger.info("Publication found: " + publication.getId());

        Establishment establishment = es.findById(publicationDTO.getEstablishmentId());
        logger.info("Establishment found: " + establishment.getId());

        publication.setPhoto(publicationDTO.getPhoto());
        publication.setTotalPrice(ps.totalPrice(publication.getId()));
        publication.setTotalPunctuation(ps.totalPunctuation(publication.getId()));
        publication.setEstablishment(establishment);
        logger.info("Publication properties updated");
        logger.info("end update publication");

        return new ResponseEntity<>(ps.updatePublication(publication), HttpStatus.OK);
    }

    @PatchMapping("/publications/price-punctuation")
    public ResponseEntity<String> totalPricePunctuation(@RequestParam(value = "id") int id) throws NotFoundException {
        logger.info("begin set total price punctuation");
        Publication publication = ps.findById(id);
        logger.info("Publication found: " + publication.getId());

        publication.setTotalPrice(ps.totalPrice(id));
        publication.setTotalPunctuation(ps.totalPunctuation(id));
        ps.updatePricePunctuation(publication);
        logger.info("Publication price and punctuation updated");
        logger.info("end set total price punctuation");

        return new ResponseEntity<>("Precio y puntuación modificados.", HttpStatus.OK);
    }

    @DeleteMapping("/publications/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) throws NotFoundException {
        logger.info("begin delete publication");
        Publication publication = ps.findById(id);
        logger.info("Publication found:" + publication.getId());
        ps.deletePublication(publication);
        logger.info("Publication deleted");

        return new ResponseEntity<>("Publication deleted.", HttpStatus.OK);
    }

    @DeleteMapping("/publications")
    public ResponseEntity<String> deleteAll() {
        ps.deleteAll();

        return new ResponseEntity<>("All publications deleted", HttpStatus.OK);
    }

    @ExceptionHandler(BadRequest.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(BadRequest br) {
        ErrorResponse errorResponse = new ErrorResponse("400", br.getMessage());
        logger.error(br.getMessage(), br);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException nfe) {
        ErrorResponse errorResponse = new ErrorResponse("404", nfe.getMessage());
        logger.error(nfe.getMessage(), nfe);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse("500", "Internal server error");
        logger.error(exception.getMessage(), exception);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
