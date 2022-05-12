package com.sanvalero.toastsapi.service;

import java.time.LocalDate;

import com.sanvalero.toastsapi.exception.NotFoundException;
import com.sanvalero.toastsapi.model.Establishment;
import com.sanvalero.toastsapi.model.Publication;
import com.sanvalero.toastsapi.model.UserModel;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PublicationService {
    Flux<Publication> findByDate(LocalDate date);

    Flux<Publication> findByDateBetween(LocalDate minDate, LocalDate maxDate);

    Flux<Publication> findByTotalPrice(float price);

    Flux<Publication> findByTotalPriceBetween(float minPrice, float maxPrice);

    Flux<Publication> findByTotalPunctuation(float punctuation);

    Flux<Publication> findByTotalPunctuationBetween(float minPunctuation, float maxPunctuation);

    Flux<Publication> findByEstablishment(Establishment establishment);

    Flux<Publication> findByUser(UserModel user);

    Flux<Publication> findByProductType(String type);

    Flux<Publication> findByDateBetweenAndTotalPriceBetweenAndTotalPunctuationBetween(LocalDate minDate,
            LocalDate maxDate, float minPrice, float maxPrice, float minPunctuation, float maxPunctiation);

    Flux<Publication> findAll();

    Mono<Publication> findById(String id) throws NotFoundException;

    float totalPrice(String id);

    float totalPunctuation(String id);

    void updatePricePunctuation(Publication publication);

    Mono<Publication> addPublication(Publication publication);

    Mono<Publication> updatePublication(Publication publication);

    void deletePublication(Publication publication);

    void deleteAll();
}
