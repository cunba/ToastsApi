package com.sanvalero.toastsapi.service;

import java.time.LocalDate;
import java.util.List;

import com.sanvalero.toastsapi.exception.NotFoundException;
import com.sanvalero.toastsapi.model.Establishment;
import com.sanvalero.toastsapi.model.Publication;
import com.sanvalero.toastsapi.model.User;
import com.sanvalero.toastsapi.model.dto.PublicationDTO;

public interface PublicationService {
    List<Publication> findByDate(LocalDate date);

    List<Publication> findByDateBetween(LocalDate minDate, LocalDate maxDate);

    List<Publication> findByTotalPrice(float price);

    List<Publication> findByTotalPriceBetween(float minPrice, float maxPrice);

    List<Publication> findByTotalPunctuation(float punctuation);

    List<Publication> findByTotalPunctuationBetween(float minPunctuation, float maxPunctuation);

    List<Publication> findByEstablishment(Establishment establishment);

    List<Publication> findByUser(User user);

    List<Publication> findAll();

    Publication findById(int id) throws NotFoundException;

    Publication addPublication(PublicationDTO publicationDTO) throws NotFoundException;

    Publication deletePublication(Publication publication);

    Publication modifyPublication(Publication publication);
}
