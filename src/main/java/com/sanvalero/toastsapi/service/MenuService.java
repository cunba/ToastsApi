package com.sanvalero.toastsapi.service;

import java.time.LocalDate;

import com.sanvalero.toastsapi.exception.NotFoundException;
import com.sanvalero.toastsapi.model.Menu;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MenuService {
    Flux<Menu> findByDate(LocalDate date);

    Flux<Menu> findByDateBetween(LocalDate minDate, LocalDate maxDate);

    Flux<Menu> findByPrice(float price);

    Flux<Menu> findByPriceBetween(float minPrice, float maxPrice);

    Flux<Menu> findByPunctuation(float punctuation);

    Flux<Menu> findByPunctuationBetween(float minPunctuation, float maxPunctuation);

    Mono<Menu> findById(String id) throws NotFoundException;

    Flux<Menu> findAll();

    Mono<Menu> addMenu(Menu menu);

    Mono<Menu> updateMenu(Menu menu);

    void deleteMenu(Menu menu);

    void deleteAll();
}
