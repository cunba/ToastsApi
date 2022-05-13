package com.sanvalero.toastsapi.repository;

import java.time.LocalDate;

import com.sanvalero.toastsapi.model.Menu;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;

@Repository
public interface MenuRepository extends ReactiveMongoRepository<Menu, String> {
    Flux<Menu> findAll();

    Flux<Menu> findByDate(LocalDate date);

    Flux<Menu> findByDateBetween(LocalDate minDate, LocalDate maxDate);

    Flux<Menu> findByPrice(float price);

    Flux<Menu> findByPriceBetween(float minPrice, float maxPrice);

    Flux<Menu> findByPunctuation(float punctuation);

    Flux<Menu> findByPunctuationBetween(float minPunctuation, float maxPunctuation);
}
