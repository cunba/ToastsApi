package com.sanvalero.toastsapi.controller.impl;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.ConstraintViolationException;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sanvalero.toastsapi.exception.BadRequestException;
import com.sanvalero.toastsapi.exception.ErrorResponse;
import com.sanvalero.toastsapi.exception.NotFoundException;
import com.sanvalero.toastsapi.model.Establishment;
import com.sanvalero.toastsapi.model.Publication;
import com.sanvalero.toastsapi.model.UserModel;
import com.sanvalero.toastsapi.model.dto.PublicationDTO;
import com.sanvalero.toastsapi.service.EstablishmentService;
import com.sanvalero.toastsapi.service.PublicationService;
import com.sanvalero.toastsapi.service.UserService;

@RestController
public class PublicationController {
    @Autowired
    private PublicationService ps;
    @Autowired
    private UserService us;
    @Autowired
    private EstablishmentService es;

    private long dateFrom = 1640995200000L;
    private final Logger logger = LoggerFactory.getLogger(PublicationController.class);

    @GetMapping(value = "/publications")
    public ResponseEntity<List<Publication>> getAll() {
        return new ResponseEntity<>(ps.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/publications/{id}")
    public ResponseEntity<Publication> getById(@PathVariable int id) throws NotFoundException {
        try {
            return new ResponseEntity<>(ps.findById(id), HttpStatus.OK);
        } catch (NotFoundException nfe) {
            logger.error("Publication not found exception with id " + id + ".", nfe);
            throw new NotFoundException("Publication with ID " + id + " does not exists.");
        }
    }

    @GetMapping(value = "/publications/date/{date}")
    public ResponseEntity<List<Publication>> getByDate(@PathVariable long date) throws BadRequestException {
        if (date < dateFrom) {
            logger.error("Publication get by date error.", new BadRequestException());
            throw new BadRequestException(
                    "The date must be in timestamp and more than " + dateFrom + " (01-01-2022 00:00:00).");
        }

        Timestamp timestamp = new Timestamp(date);
        LocalDate dateLocal = timestamp.toLocalDateTime().toLocalDate();

        return new ResponseEntity<>(ps.findByDate(dateLocal), HttpStatus.OK);
    }

    @GetMapping(value = "/publications/date/between")
    public ResponseEntity<List<Publication>> getByDateBetween(@RequestParam(value = "minDate") long minDate,
            @RequestParam(value = "maxDate") long maxDate) throws BadRequestException {

        if (minDate < dateFrom || maxDate < dateFrom) {
            logger.error("Publication get by date between error.", new BadRequestException());
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

        return new ResponseEntity<>(ps.findByDateBetween(minDateLocal, maxDateLocal), HttpStatus.OK);
    }

    @GetMapping(value = "/publications/price/{price}")
    public ResponseEntity<List<Publication>> getByTotalPrice(@PathVariable float price) throws BadRequestException {
        if (price < 0) {
            logger.error("Publication get by price error.", new BadRequestException());
            throw new BadRequestException("The price must be 0 or more.");
        }
        return new ResponseEntity<>(ps.findByTotalPrice(price), HttpStatus.OK);
    }

    @GetMapping(value = "/publications/price/between")
    public ResponseEntity<List<Publication>> getByPriceBetween(@RequestParam(value = "minPrice") float minPrice,
            @RequestParam(value = "maxPrice") float maxPrice) throws BadRequestException {

        if (minPrice < 0 || maxPrice < 0) {
            logger.error("Publication get by price error.", new BadRequestException());
            throw new BadRequestException("The price must be 0 or more.");
        }
        float templatePrice = 0;
        if (minPrice > maxPrice) {
            templatePrice = minPrice;
            minPrice = maxPrice;
            maxPrice = templatePrice;
        }

        return new ResponseEntity<>(ps.findByTotalPriceBetween(minPrice, maxPrice), HttpStatus.OK);
    }

    @GetMapping(value = "/publications/score/{score}")
    public ResponseEntity<List<Publication>> getByScore(@PathVariable float score)
            throws BadRequestException {
        if (score < 0 || score > 5) {
            logger.error("Publication get by puntuation error.", new BadRequestException());
            throw new BadRequestException("The score must be between 0 and 5.");
        }
        return new ResponseEntity<>(ps.findByTotalScore(score), HttpStatus.OK);
    }

    @GetMapping(value = "/publications/score/between")
    public ResponseEntity<List<Publication>> getByScoreBetween(
            @RequestParam(value = "minScore") float minScore,
            @RequestParam(value = "maxScore") float maxScore) throws BadRequestException {

        if (minScore < 0 || minScore > 5 || maxScore < 0 || maxScore > 5) {
            logger.error("Publication get by puntuation between error.", new BadRequestException());
            throw new BadRequestException("The score must be between 0 and 5.");
        }
        float templateScore = 0;
        if (minScore > maxScore) {
            templateScore = minScore;
            minScore = maxScore;
            maxScore = templateScore;
        }

        return new ResponseEntity<>(ps.findByTotalScoreBetween(minScore, maxScore), HttpStatus.OK);
    }

    @GetMapping(value = "/publications/establishment/{id}")
    public ResponseEntity<List<Publication>> getByEstablishmentId(@PathVariable int id)
            throws NotFoundException {

        try {
            Establishment establishment = es.findById(id);
            return new ResponseEntity<>(ps.findByEstablishment(establishment), HttpStatus.OK);
        } catch (NotFoundException nfe) {
            logger.error("Establishment not found exception with id " + id + ".", nfe);
            throw new NotFoundException("Establishment with ID " + id + " does not exists.");
        }
    }

    @GetMapping(value = "/publications/user/{id}")
    public ResponseEntity<List<Publication>> getByUserId(@PathVariable int id) throws NotFoundException {
        try {
            UserModel user = us.findById(id);
            return new ResponseEntity<>(ps.findByUser(user), HttpStatus.OK);
        } catch (NotFoundException nfe) {
            logger.error("User not found exception with id " + id + ".", nfe);
            throw new NotFoundException("User with ID " + id + " does not exists.");
        }
    }

    @GetMapping(value = "/publications/type/{type}")
    public ResponseEntity<List<Publication>> getByProductType(@PathVariable String type) {
        return new ResponseEntity<>(ps.findByProductType(type), HttpStatus.OK);
    }

    @GetMapping(value = "/publications/date/price/score/between")
    public ResponseEntity<List<Publication>> getByDateBetweenTotalPriceBetweenTotalScoreBetween(
            @RequestParam(value = "minDate") long minDate,
            @RequestParam(value = "maxDate") long maxDate,
            @RequestParam(value = "minPrice") float minPrice,
            @RequestParam(value = "maxPrice") float maxPrice,
            @RequestParam(value = "minScore") float minScore,
            @RequestParam(value = "maxScore") float maxScore) throws BadRequestException {

        if (minDate < dateFrom || maxDate < dateFrom) {
            logger.error("Publication get by date, price and score between error.", new BadRequestException());
            throw new BadRequestException(
                    "The dates must be in timestamp and more than " + dateFrom + " (01-01-2022 00:00:00).");
        }
        if (minScore < 0 || minScore > 5 || maxScore < 0 || maxScore > 5) {
            logger.error("Publication get by date, price and puntuation between error.", new BadRequestException());
            throw new BadRequestException("The score must be between 0 and 5.");
        }
        if (minPrice < 0 || maxPrice < 0) {
            logger.error("Publication get by date, price and score error.", new BadRequestException());
            throw new BadRequestException("The price must be 0 or more.");
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

        float changerPrice = 0;
        if (minPrice > maxPrice) {
            changerPrice = minPrice;
            minPrice = maxPrice;
            maxPrice = changerPrice;
        }

        float changerScore = 0;
        if (minScore > maxScore) {
            changerScore = minScore;
            minScore = maxScore;
            maxScore = changerScore;
        }

        List<Publication> publications = ps.findByDateBetweenAndTotalPriceBetweenAndTotalScoreBetween(
                minDateLocal, maxDateLocal, minPrice, maxPrice, minScore, maxScore);

        return new ResponseEntity<>(publications, HttpStatus.OK);
    }

    @Secured({ "ROLE_USER", "ROLE_ADMIN" })
    @PostMapping(value = "/publications")
    public ResponseEntity<Publication> create(@RequestBody PublicationDTO publicationDTO) throws NotFoundException {
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
        publication.setPhoto(publicationDTO.getPhoto());
        publication.setTotalPrice(0);
        publication.setTotalScore(0);
        publication.setDate(LocalDate.now());
        publication.setEstablishment(establishment);
        publication.setUser(user);
        publication.setTotalPrice(publicationDTO.getTotalPrice());
        publication.setTotalScore(publicationDTO.getTotalScore());
        logger.info("Publication mapped");
        logger.info("end create publication");

        return new ResponseEntity<>(ps.addPublication(publication), HttpStatus.CREATED);
    }

    @Secured({ "ROLE_USER", "ROLE_ADMIN" })
    @PutMapping(value = "/publications/{id}")
    public ResponseEntity<Publication> update(@RequestBody PublicationDTO publicationDTO,
            @PathVariable int id) throws NotFoundException {

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

        publication.setPhoto(publicationDTO.getPhoto());
        publication.setTotalPrice(publicationDTO.getTotalPrice());
        publication.setTotalScore(publicationDTO.getTotalScore());
        publication.setEstablishment(establishment);

        Publication toPrint = ps.updatePublication(publication);
        logger.info("Publication properties updated");
        logger.info("end update publication");

        return new ResponseEntity<>(toPrint, HttpStatus.OK);
    }

    @Secured({ "ROLE_USER", "ROLE_ADMIN" })
    @DeleteMapping(value = "/publications/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) throws NotFoundException {
        logger.info("begin delete publication");
        try {
            Publication publication = ps.findById(id);
            logger.info("Publication found:" + publication.getId());
            ps.deletePublication(publication);
            logger.info("Publication deleted");

            return new ResponseEntity<>("Publication deleted.", HttpStatus.OK);
        } catch (NotFoundException nfe) {
            logger.error("Publication not found exception with id " + id + ".", nfe);
            throw new NotFoundException("Publication with ID " + id + " does not exists.");
        }
    }

    @Secured({ "ROLE_ADMIN" })
    @DeleteMapping(value = "/publications")
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
