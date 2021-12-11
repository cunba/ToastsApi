package com.sanvalero.toastsapi.service;

import java.time.LocalDate;
import java.util.List;

import com.sanvalero.toastsapi.exception.NotFoundException;
import com.sanvalero.toastsapi.model.Menu;
import com.sanvalero.toastsapi.model.Publication;

public interface ProductTemplateService<T> {
    List<T> findByDate(LocalDate date);

    List<T> findByDateBetween(LocalDate minDate, LocalDate maxDate);

    List<T> findByPrice(float price);

    List<T> findByPriceBetween(float minPrice, float maxPrice);

    List<T> findByPunctuation(float punctuation);

    List<T> findByPunctuationBetween(float minPunctuation, float maxPunctuation);

    List<T> findByWithMenu(boolean withMenu);

    List<T> findByMenu(Menu menu);

    List<T> findByPublication(Publication publication);

    List<T> findAll();

    T findById(int id) throws NotFoundException;
}
