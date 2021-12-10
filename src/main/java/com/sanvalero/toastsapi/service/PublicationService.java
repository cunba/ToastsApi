package com.sanvalero.toastsapi.service;

import java.time.LocalDate;
import java.util.List;

import com.sanvalero.toastsapi.model.Establishment;
import com.sanvalero.toastsapi.model.Publication;
import com.sanvalero.toastsapi.model.User;

public interface PublicationService {
    List<Publication> findByDate(LocalDate date);

    List<Publication> findByDateBetween(LocalDate minDate, LocalDate maxDate);

    List<Publication> findByTotalPrice(float price);

    List<Publication> findByTotalPriceBetween(float minPrice, float maxPrice);

    List<Publication> findByTotalPunctuation(float punctuation);

    List<Publication> findByTotalPunctuationBetween(float minPunctuation, float maxPunctuation);

    List<Publication> findByEstablishment(Establishment establishment);

    List<Publication> findByUser(User user);

    List<Publication> findAllPublications();

    Publication findById(int id);

    Publication addPublication(Publication publication);

    Publication deletePublication(int id);

    Publication modifyPublication(Publication publication, int id);
}
