package com.sanvalero.toastsapi.service;

import java.time.LocalDate;
import java.util.List;

import com.sanvalero.toastsapi.model.Establishment;

public interface EstablishmentService {
    List<Establishment> findByName(String name);

    List<Establishment> findByCreationDate(LocalDate date);

    List<Establishment> findByCreationDateBetween(LocalDate minDate, LocalDate maxDate);

    List<Establishment> findByOpen(boolean open);

    List<Establishment> findByLocation(String location);

    List<Establishment> findByPunctuation(float punctuation);

    List<Establishment> findByPunctuationBetween(float minPunctuation, float maxPunctuation);
    
    Establishment addEstablishment(Establishment establishment);

    Establishment deleteEstablishment(int id);

    Establishment modifyEstablishment(Establishment establishment, int id);
}
