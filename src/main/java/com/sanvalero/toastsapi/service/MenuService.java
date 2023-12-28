package com.sanvalero.toastsapi.service;

import java.time.LocalDate;
import java.util.List;

import com.sanvalero.toastsapi.exception.NotFoundException;
import com.sanvalero.toastsapi.model.Menu;

public interface MenuService {
    List<Menu> findByDate(LocalDate date);

    List<Menu> findByDateBetween(LocalDate minDate, LocalDate maxDate);

    List<Menu> findByPrice(float price);

    List<Menu> findByPriceBetween(float minPrice, float maxPrice);

    List<Menu> findByScore(float score);

    List<Menu> findByScoreBetween(float minScore, float maxScore);

    Menu findById(int id) throws NotFoundException;

    List<Menu> findAll();

    Menu addMenu(Menu menu);

    Menu updateMenu(Menu menu);

    void deleteMenu(Menu menu);

    void deleteAll();
}
