package com.sanvalero.toastsapi.service;

import java.time.LocalDate;

import com.sanvalero.toastsapi.exception.NotFoundException;
import com.sanvalero.toastsapi.model.Establishment;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EstablishmentService {
    Mono<Establishment> findByName(String name);

    Flux<Establishment> findByCreationDate(LocalDate date);

    Flux<Establishment> findByCreationDateBetween(LocalDate minDate, LocalDate maxDate);

    Flux<Establishment> findByOpen(boolean open);

    Flux<Establishment> findByLocation(String location);

    Flux<Establishment> findByPunctuation(float punctuation);

    Flux<Establishment> findByPunctuationBetween(float minPunctuation, float maxPunctuation);

    Mono<Establishment> findById(String id) throws NotFoundException;

    Flux<Establishment> findAll();

    Mono<Establishment> addEstablishment(Establishment establishment);

    Mono<Establishment> updateEstablishment(Establishment establishment);

    void deleteEstablishment(Establishment establishment);

    void deleteAll();

    float sumPunctuation(String id);

    void updatePunctuation(Establishment establishment);
}
