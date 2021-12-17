package com.sanvalero.toastsapi.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.sanvalero.toastsapi.exception.ErrorResponse;
import com.sanvalero.toastsapi.exception.NotFoundException;
import com.sanvalero.toastsapi.model.Menu;
import com.sanvalero.toastsapi.service.MenuService;

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
public class MenuController {
    @Autowired
    private MenuService ms;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @GetMapping("/menus/{dateString}")
    public List<Menu> getByDate(@PathVariable String dateString) {
        LocalDate date = LocalDate.parse(dateString, formatter);

        return ms.findByDate(date);
    }

    @GetMapping("/menus/{minDateString}-{maxDateString}")
    public List<Menu> getByDateBetween(@PathVariable String minDateString,
            @PathVariable String maxDateString) {

        LocalDate minDate = LocalDate.parse(minDateString, formatter);
        LocalDate maxDate = LocalDate.parse(maxDateString, formatter);

        LocalDate changerDate = LocalDate.now();
        if (minDate.isAfter(maxDate)) {
            changerDate = minDate;
            minDate = maxDate;
            maxDate = changerDate;
        }

        return ms.findByDateBetween(minDate, maxDate);
    }

    @GetMapping("/menus/{price}")
    public List<Menu> getByPrice(@PathVariable float price) {
        return ms.findByPrice(price);
    }

    @GetMapping("/menus/{minPrice}-{maxPrice}")
    public List<Menu> getByPriceBetween(@PathVariable float minPrice,
            @PathVariable float maxPrice) {

        float templatePrice = 0;
        if (minPrice > maxPrice) {
            templatePrice = minPrice;
            minPrice = maxPrice;
            maxPrice = templatePrice;
        }

        return ms.findByPriceBetween(minPrice, maxPrice);
    }

    @GetMapping("/menus/{punctuation}")
    public List<Menu> getByPunctuation(@PathVariable float punctuation) {
        return ms.findByPunctuation(punctuation);
    }

    @GetMapping("/menus/{minPunctuation}-{maxPunctuation}")
    public List<Menu> getByPunbtuationBetween(@PathVariable float minPunctuation,
            @PathVariable float maxPunctuation) {

        float templatePunctuation = 0;
        if (minPunctuation > maxPunctuation) {
            templatePunctuation = minPunctuation;
            minPunctuation = maxPunctuation;
            maxPunctuation = templatePunctuation;
        }

        return ms.findByPunctuationBetween(minPunctuation, maxPunctuation);
    }

    @GetMapping("/menu/{id}")
    public Menu getById(@PathVariable int id) throws NotFoundException {
        return ms.findById(id);
    }

    @GetMapping("/menus")
    public List<Menu> getAll() {
        return ms.findAll();
    }

    @PostMapping("/menu")
    public Menu create(@RequestBody Menu menu) {
        menu.setDate(LocalDate.now());
        return ms.addMenu(menu);
    }

    @PutMapping("/menu/{id}")
    public Menu update(@PathVariable int id, @RequestBody Menu menu) throws NotFoundException {
        Menu menuToUpdate = ms.findById(id);
        menuToUpdate.setDate(menu.getDate());
        menuToUpdate.setPrice(menu.getPrice());
        menuToUpdate.setPunctuation(menu.getPunctuation());

        return ms.modifyMenu(menuToUpdate);
    }

    @DeleteMapping("/menu/{id}")
    public Menu delete(@PathVariable int id) throws NotFoundException {
        Menu menu = ms.findById(id);
        ms.deleteMenu(menu);
        return menu;
    }

    @DeleteMapping("/menus")
    public String deleteAll() {
        ms.deleteAll();

        return "All menus deleted";
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException nfe) {
        ErrorResponse errorResponse = new ErrorResponse("404", nfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
