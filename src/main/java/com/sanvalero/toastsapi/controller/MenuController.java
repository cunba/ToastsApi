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

    @GetMapping("/menus/date={dateString}")
    public List<Menu> getMenusByDate(@PathVariable String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.parse(dateString, formatter);
        return ms.findByDate(date);
    }

    @GetMapping("/menus/minDate={minDateString}-maxDate={maxDateString}")
    public List<Menu> getMenusByDateBetween(@PathVariable String minDateString,
                                            @PathVariable String maxDateString) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate minDate = LocalDate.parse(minDateString, formatter);
        LocalDate maxDate = LocalDate.parse(maxDateString, formatter);
        return ms.findByDateBetween(minDate, maxDate);
    }

    @GetMapping("/menus/price={price}")
    public List<Menu> getMenusByPrice(@PathVariable float price) {
        return ms.findByPrice(price);
    }

    @GetMapping("/menus/minPrice={minPrice}-maxPrice={maxPrice}")
    public List<Menu> getMenusByPriceBetween(@PathVariable float minPrice,
            @PathVariable float maxPrice) {
        return ms.findByPriceBetween(minPrice, maxPrice);
    }

    @GetMapping("/menus/punctuation={punctuation}")
    public List<Menu> getMenusByPunctuation(@PathVariable float punctuation) {
        return ms.findByPunctuation(punctuation);
    }

    @GetMapping("/menus/minPunctuation={minPunctuation}-maxPunctuation={maxPunctuation}")
    public List<Menu> getMenusByPunbtuationBetween(@PathVariable float minPunctuation,
            @PathVariable float maxPunctuation) {
        return ms.findByPunctuationBetween(minPunctuation, maxPunctuation);
    }

    @GetMapping("/menu/id={id}")
    public Menu getMenuById(@PathVariable int id) throws NotFoundException {
        return ms.findById(id);
    }

    @GetMapping("/menus")
    public List<Menu> getAllMenus() {
        return ms.findAll();
    }

    @PostMapping("/menu")
    public Menu create(@RequestBody Menu menu) {
        return ms.addMenu(menu);
    }

    @DeleteMapping("/menu/id={id}")
    public Menu delete(@PathVariable int id) throws NotFoundException {
        Menu menu = getMenuById(id);
        ms.deleteMenu(menu);
        return menu;
    }

    @PutMapping("menu/id={id}")
    public Menu modify(@PathVariable int id, @RequestBody Menu menu) throws NotFoundException {
        Menu menuToModify = ms.findById(id);
        menuToModify.setDate(menu.getDate());
        menuToModify.setPrice(menu.getPrice());
        menuToModify.setPunctuation(menu.getPunctuation());

        return ms.modifyMenu(menuToModify);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException bnfe) {
        ErrorResponse errorResponse = new ErrorResponse("404", bnfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
