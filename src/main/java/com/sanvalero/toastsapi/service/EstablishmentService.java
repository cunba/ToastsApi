package com.sanvalero.toastsapi.service;

import java.time.LocalDate;
import java.util.List;

import com.sanvalero.toastsapi.exception.NotFoundException;
import com.sanvalero.toastsapi.model.Establishment;

public interface EstablishmentService {
    Establishment findByName(String name);

    List<Establishment> findByCreationDate(LocalDate date);

    List<Establishment> findByCreationDateBetween(LocalDate minDate, LocalDate maxDate);

    List<Establishment> findByOpen(boolean open);

    List<Establishment> findByLocation(String location);

    List<Establishment> findByPunctuation(float punctuation);

    List<Establishment> findByPunctuationBetween(float minPunctuation, float maxPunctuation);

    Establishment findById(int id) throws NotFoundException;

    List<Establishment> findAll();

    Establishment addEstablishment(Establishment establishment);
    
    Establishment modifyEstablishment(Establishment establishment);

    Establishment deleteEstablishment(Establishment establishment);

    void deleteAll();
}
