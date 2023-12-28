package com.sanvalero.toastsapi.service;

import java.time.LocalDate;
import java.util.List;

import com.sanvalero.toastsapi.exception.NotFoundException;
import com.sanvalero.toastsapi.model.Establishment;
import com.sanvalero.toastsapi.model.Publication;
import com.sanvalero.toastsapi.model.UserModel;

public interface PublicationService {
    List<Publication> findByDate(LocalDate date);

    List<Publication> findByDateBetween(LocalDate minDate, LocalDate maxDate);

    List<Publication> findByTotalPrice(float price);

    List<Publication> findByTotalPriceBetween(float minPrice, float maxPrice);

    List<Publication> findByTotalScore(float score);

    List<Publication> findByTotalScoreBetween(float minScore, float maxScore);

    List<Publication> findByEstablishment(Establishment establishment);

    List<Publication> findByUser(UserModel user);

    List<Publication> findByProductType(String type);

    List<Publication> findByDateBetweenAndTotalPriceBetweenAndTotalScoreBetween(LocalDate minDate, LocalDate maxDate, float minPrice, float maxPrice, float minScore, float maxPunctiation);

    List<Publication> findAll();

    Publication findById(int id) throws NotFoundException;

    Publication addPublication(Publication publication);

    Publication updatePublication(Publication publication);

    void deletePublication(Publication publication);

    void deleteAll();
}
