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

    List<Establishment> findByScore(float score);

    List<Establishment> findByScoreBetween(float minScore, float maxScore);

    Establishment findById(int id) throws NotFoundException;

    List<Establishment> findAll();

    Establishment addEstablishment(Establishment establishment);
    
    Establishment updateEstablishment(Establishment establishment);

    void deleteEstablishment(Establishment establishment);

    void deleteAll();

    float sumScore(int id);

    float countPublications(int id);

    void updateScore(Establishment establishment);
}
