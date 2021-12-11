package com.sanvalero.toastsapi.controller;

import java.time.LocalDate;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MenuController {
    @Autowired
    private MenuService ms;

    @GetMapping("/menus/{date}")
    public List<Menu> getMenusByDate(@RequestParam(name = "date" ) LocalDate date) {
        return ms.findByDate(date);
    }

    @GetMapping("/menus/minDate={minDate}-maxDate={maxDate}")
    public List<Menu> getMenusByDateBetween(@PathVariable LocalDate minDate,
                                            @PathVariable LocalDate maxDate) {
        return ms.findByDateBetween(minDate, maxDate);
    }

    @GetMapping("/menus/{price}")
    public List<Menu> getMenusByPrice(@PathVariable float price) {
        return ms.findByPrice(price);
    }

    @GetMapping("/menus/{minPrice}{maxPrice}")
    public List<Menu> getMenusByPriceBetween(@PathVariable float minPrice,
                                             @PathVariable float maxPrice) {
        return ms.findByPriceBetween(minPrice, maxPrice);
    }

    @GetMapping("/menus/{punctuation}")
    public List<Menu> getMenusByPunctuation(@PathVariable float punctuation) {
        return ms.findByPunctuation(punctuation);
    }

    @GetMapping("/menus/minPunctuation={minPunctuation}-maxPunctuation={maxPunctuation}")
    public List<Menu> getMenusByPunbtuationBetween(@PathVariable float minPunctuation,
                                                   @PathVariable float maxPunctuation) {
        return ms.findByPunctuationBetween(minPunctuation, maxPunctuation);
    }

    @GetMapping("/menu/{id}")
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

    @DeleteMapping("/menu/{id}")
    public Menu delete(@PathVariable int id) throws NotFoundException {
        Menu menu = getMenuById(id);
        ms.deleteMenu(menu);
        return menu;
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException bnfe) {
        ErrorResponse errorResponse = new ErrorResponse("404", bnfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
