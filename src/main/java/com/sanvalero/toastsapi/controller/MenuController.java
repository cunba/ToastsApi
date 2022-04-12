package com.sanvalero.toastsapi.controller;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import com.sanvalero.toastsapi.exception.BadRequestException;
import com.sanvalero.toastsapi.exception.ErrorResponse;
import com.sanvalero.toastsapi.exception.NotFoundException;
import com.sanvalero.toastsapi.model.Menu;
import com.sanvalero.toastsapi.service.MenuService;

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

    private final Logger logger = LoggerFactory.getLogger(MenuController.class);

    @GetMapping("/menus/date/{dateTimestamp}")
    public ResponseEntity<List<Menu>> getByDate(@PathVariable long dateTimestamp) {
        Timestamp timestamp = new Timestamp(dateTimestamp);
        LocalDate date = timestamp.toLocalDateTime().toLocalDate();

        return new ResponseEntity<>(ms.findByDate(date), HttpStatus.OK);
    }

    @GetMapping("/menus/date/between/")
    public ResponseEntity<List<Menu>> getByDateBetween(@RequestParam(value = "minDate") long minDateTimestamp,
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

        return new ResponseEntity<>(ms.findByDateBetween(minDate, maxDate), HttpStatus.OK);
    }

    @GetMapping("/menus/price/{price}")
    public ResponseEntity<List<Menu>> getByPrice(@PathVariable float price) {
        return new ResponseEntity<>(ms.findByPrice(price), HttpStatus.OK);
    }

    @GetMapping("/menus/price/between/")
    public ResponseEntity<List<Menu>> getByPriceBetween(@RequestParam(value = "minPrice") float minPrice,
            @RequestParam(value = "maxPrice") float maxPrice) {

        float templatePrice = 0;
        if (minPrice > maxPrice) {
            templatePrice = minPrice;
            minPrice = maxPrice;
            maxPrice = templatePrice;
        }

        return new ResponseEntity<>(ms.findByPriceBetween(minPrice, maxPrice), HttpStatus.OK);
    }

    @GetMapping("/menus/punctuation/{punctuation}")
    public ResponseEntity<List<Menu>> getByPunctuation(@PathVariable float punctuation) {
        return new ResponseEntity<>(ms.findByPunctuation(punctuation), HttpStatus.OK);
    }

    @GetMapping("/menus/punctuation/betweem")
    public ResponseEntity<List<Menu>> getByPunbtuationBetween(@RequestParam(value = "minPunctuation") float minPunctuation,
            @RequestParam(value = "maxPunctuation") float maxPunctuation) {

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
        return new ResponseEntity<>(ms.findById(id), HttpStatus.OK);
    }

    @GetMapping("/menus")
    public ResponseEntity<List<Menu>> getAll() {
        return new ResponseEntity<>(ms.findAll(), HttpStatus.OK);
    }

    @PostMapping("/menus")
    public ResponseEntity<Menu> create(@RequestBody Menu menu) {
        menu.setDate(LocalDate.now());
        return new ResponseEntity<>(ms.addMenu(menu), HttpStatus.OK);
    }

    @PutMapping("/menus/{id}")
    public ResponseEntity<Menu> update(@PathVariable int id, @RequestBody Menu menu) throws NotFoundException {
        logger.info("begin update menu");
        Menu menuToUpdate = ms.findById(id);
        logger.info("Menu found: " + menu.getId());
        menuToUpdate.setPrice(menu.getPrice());
        menuToUpdate.setPunctuation(menu.getPunctuation());
        logger.info("Menu properties updated");
        logger.info("end update menu");

        return new ResponseEntity<>(ms.updateMenu(menuToUpdate), HttpStatus.OK);
    }

    @DeleteMapping("/menus/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) throws NotFoundException {
        logger.info("begin delete menu");
        Menu menu = ms.findById(id);
        logger.info("Menu found: " + menu.getId());
        ms.deleteMenu(menu);
        logger.info("Menu deleted");
        logger.info("end delete menu");

        return new ResponseEntity<>("Menu deleted.", HttpStatus.OK);
    }

    @DeleteMapping("/menus")
    public ResponseEntity<String> deleteAll() {
        ms.deleteAll();

        return new ResponseEntity<>("All menus deleted", HttpStatus.OK);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException br) {
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
