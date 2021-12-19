package com.sanvalero.toastsapi.service;

import java.time.LocalDate;
import java.util.List;

import com.sanvalero.toastsapi.exception.NotFoundException;
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

    List<Publication> findByProductType(String type);

    List<Publication> findAll();

    Publication findById(int id) throws NotFoundException;

    float totalPrice(int id);

    float totalPunctuation(int id);

    void updatePricePunctuation(Publication publication);

    Publication addPublication(Publication publication);

    Publication updatePublication(Publication publication);

    void deletePublication(Publication publication);

    void deleteAll();
}
