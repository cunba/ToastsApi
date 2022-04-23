package com.sanvalero.toastsapi.controller;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import com.sanvalero.toastsapi.exception.BadRequestException;
import com.sanvalero.toastsapi.exception.ErrorResponse;
import com.sanvalero.toastsapi.exception.NotFoundException;
import com.sanvalero.toastsapi.model.Menu;
import com.sanvalero.toastsapi.model.dto.MenuDTO;
import com.sanvalero.toastsapi.service.MenuService;

import org.modelmapper.ModelMapper;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MenuController {
    @Autowired
    private MenuService ms;

    private long dateFrom = 1640995200000L;
    private final Logger logger = LoggerFactory.getLogger(MenuController.class);

    @GetMapping("/menus/date/{date}")
    public ResponseEntity<List<Menu>> getByDate(@PathVariable long date) throws BadRequestException {
        if (date < dateFrom) {
            logger.error("Establishment get by date error.", new BadRequestException());
            throw new BadRequestException(
                    "The date must be in timestamp and more than " + dateFrom + " (01-01-2022 00:00:00).");
        }

        Timestamp timestamp = new Timestamp(date);
        LocalDate dateLocal = timestamp.toLocalDateTime().toLocalDate();

        return new ResponseEntity<>(ms.findByDate(dateLocal), HttpStatus.OK);
    }

    @GetMapping("/menus/date/between")
    public ResponseEntity<List<Menu>> getByDateBetween(@RequestParam(value = "minDate") long minDate,
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

        return new ResponseEntity<>(ms.findByDateBetween(minDateLocal, maxDateLocal), HttpStatus.OK);
    }

    @GetMapping("/menus/price/{price}")
    public ResponseEntity<List<Menu>> getByPrice(@PathVariable float price) throws BadRequestException {
        if (price < 0) {
            logger.error("Establishment get by price error.", new BadRequestException());
            throw new BadRequestException("The price must be 0 or more.");
        }

        return new ResponseEntity<>(ms.findByPrice(price), HttpStatus.OK);
    }

    @GetMapping("/menus/price/between")
    public ResponseEntity<List<Menu>> getByPriceBetween(@RequestParam(value = "minPrice") float minPrice,
            @RequestParam(value = "maxPrice") float maxPrice) throws BadRequestException {

        if (minPrice < 0 || maxPrice < 0) {
            logger.error("Establishment get by price error.", new BadRequestException());
            throw new BadRequestException("The price must be 0 or more.");
        }
        float templatePrice = 0;
        if (minPrice > maxPrice) {
            templatePrice = minPrice;
            minPrice = maxPrice;
            maxPrice = templatePrice;
        }

        return new ResponseEntity<>(ms.findByPriceBetween(minPrice, maxPrice), HttpStatus.OK);
    }

    @GetMapping("/menus/punctuation/{punctuation}")
    public ResponseEntity<List<Menu>> getByPunctuation(@PathVariable float punctuation) throws BadRequestException {
        if (punctuation < 0 || punctuation > 5) {
            logger.error("Establishment get by puntuation error.", new BadRequestException());
            throw new BadRequestException("The punctuation must be between 0 and 5.");
        }
        return new ResponseEntity<>(ms.findByPunctuation(punctuation), HttpStatus.OK);
    }

    @GetMapping("/menus/punctuation/between")
    public ResponseEntity<List<Menu>> getByPunbtuationBetween(
            @RequestParam(value = "minPunctuation") float minPunctuation,
            @RequestParam(value = "maxPunctuation") float maxPunctuation) throws BadRequestException {

        if (minPunctuation < 0 || minPunctuation > 5 || maxPunctuation < 0 || maxPunctuation > 5) {
            logger.error("Establishment get by puntuation between error.", new BadRequestException());
            throw new BadRequestException("The punctuation must be between 0 and 5.");
        }

        float templatePunctuation = 0;
        if (minPunctuation > maxPunctuation) {
            templatePunctuation = minPunctuation;
            minPunctuation = maxPunctuation;
            maxPunctuation = templatePunctuation;
        }

        return new ResponseEntity<>(ms.findByPunctuationBetween(minPunctuation, maxPunctuation), HttpStatus.OK);
    }

    @GetMapping("/menus/{id}")
    public ResponseEntity<Menu> getById(@PathVariable int id) throws NotFoundException {
        try {
            Menu menu = ms.findById(id);
            return new ResponseEntity<>(menu, HttpStatus.OK);
        } catch (NotFoundException nfe) {
            logger.error("Menu not found exception with id " + id + ".", nfe);
            throw new NotFoundException("Menu with ID " + id + " does not exists.");
        }
    }

    @GetMapping("/menus")
    public ResponseEntity<List<Menu>> getAll() {
        return new ResponseEntity<>(ms.findAll(), HttpStatus.OK);
    }

    @PostMapping("/menus")
    public ResponseEntity<Menu> create(@RequestBody MenuDTO menuDTO) {
        logger.info("begin create menu");
        ModelMapper mapper = new ModelMapper();
        Menu menu = mapper.map(menuDTO, Menu.class);
        menu.setDate(LocalDate.now());
        logger.info("Menu mapped");
        Menu toPrint = ms.addMenu(menu);
        logger.info("Menu created");
        logger.info("end create establishment");

        return new ResponseEntity<>(toPrint, HttpStatus.CREATED);
    }

    @PutMapping("/menus/{id}")
    public ResponseEntity<Menu> update(@PathVariable int id, @RequestBody MenuDTO menuDTO) throws NotFoundException {
        logger.info("begin update menu");
        try {
            Menu menuToUpdate = ms.findById(id);
            logger.info("Menu found: " + id);
            menuToUpdate.setPrice(menuDTO.getPrice());
            menuToUpdate.setPunctuation(menuDTO.getPunctuation());
            logger.info("Menu properties updated");
            logger.info("end update menu");

            return new ResponseEntity<>(ms.updateMenu(menuToUpdate), HttpStatus.OK);
        } catch (NotFoundException nfe) {
            logger.error("Menu not found exception with id " + id + ".", nfe);
            throw new NotFoundException("Menu with ID " + id + " does not exists.");
        }
    }

    @DeleteMapping("/menus/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) throws NotFoundException {
        logger.info("begin delete menu");
        try {
            Menu menu = ms.findById(id);
            logger.info("Menu found: " + menu.getId());
            ms.deleteMenu(menu);
            logger.info("Menu deleted");
            logger.info("end delete menu");

            return new ResponseEntity<>("Menu deleted.", HttpStatus.OK);
        } catch (NotFoundException nfe) {
            logger.error("Menu not found exception with id " + id + ".", nfe);
            throw new NotFoundException("Menu with ID " + id + " does not exists.");
        }
    }

    @DeleteMapping("/menus")
    public ResponseEntity<String> deleteAll() {
        ms.deleteAll();

        return new ResponseEntity<>("All menus deleted", HttpStatus.OK);
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
