package com.sanvalero.toastsapi.repository;

import java.time.LocalDate;
import java.util.List;

import com.sanvalero.toastsapi.model.Establishment;
import com.sanvalero.toastsapi.model.Publication;
import com.sanvalero.toastsapi.model.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicationRepository extends CrudRepository<Publication, Integer> {
    List<Publication> findAll();

    List<Publication> findByDate(LocalDate date);

    List<Publication> findByDateBetween(LocalDate minDate, LocalDate maxDate);

    List<Publication> findByTotalPrice(float price);

    List<Publication> findByTotalPriceBetween(float minPrice, float maxPrice);

    List<Publication> findByTotalPunctuation(float punctuation);

    List<Publication> findByTotalPunctuationBetween(float minPunctuation, float maxPunctuation);

    List<Publication> findByEstablishment(Establishment establishment);

    List<Publication> findByUser(User user);
}
