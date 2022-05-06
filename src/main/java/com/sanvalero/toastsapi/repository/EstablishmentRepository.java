package com.sanvalero.toastsapi.repository;

import java.time.LocalDate;

import com.sanvalero.toastsapi.model.Establishment;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface EstablishmentRepository extends ReactiveCrudRepository<Establishment, Integer> {
    Flux<Establishment> findAll();

    Mono<Establishment> findByName(String name);

    Flux<Establishment> findByCreationDate(LocalDate date);

    Flux<Establishment> findByCreationDateBetween(LocalDate minDate, LocalDate maxDate);

    Flux<Establishment> findByOpen(boolean open);

    Flux<Establishment> findByLocation(String location);

    Flux<Establishment> findByPunctuation(float punctuation);

    Flux<Establishment> findByPunctuationBetween(float minPunctuation, float maxPunctuation);

    @Query(value = "SELECT SUM(total_punctuation) FROM publications WHERE establishment_id = :id", nativeQuery = true)
    float sumPunctuation(int id);
}
