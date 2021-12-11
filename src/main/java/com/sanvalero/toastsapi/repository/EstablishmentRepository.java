package com.sanvalero.toastsapi.repository;

import java.time.LocalDate;
import java.util.List;

import com.sanvalero.toastsapi.model.Establishment;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstablishmentRepository extends CrudRepository<Establishment, Integer> {
    List<Establishment> findAll();

    List<Establishment> findByName(String name);

    List<Establishment> findByCreationDate(LocalDate date);

    List<Establishment> findByCreationDateBetween(LocalDate minDate, LocalDate maxDate);

    List<Establishment> findByOpen(boolean open);

    List<Establishment> findByLocation(String location);

    List<Establishment> findByPunctuation(float punctuation);

    List<Establishment> findByPunctuationBetween(float minPunctuation, float maxPunctuation);
}
