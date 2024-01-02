package com.sanvalero.toastsapi.repository;

import java.time.LocalDate;
import java.util.List;

import com.sanvalero.toastsapi.model.Establishment;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstablishmentRepository extends CrudRepository<Establishment, Integer> {
    List<Establishment> findAll();

    Establishment findByName(String name);

    List<Establishment> findByCreationDate(LocalDate date);

    List<Establishment> findByCreationDateBetween(LocalDate minDate, LocalDate maxDate);

    List<Establishment> findByOpen(boolean open);

    List<Establishment> findByLocation(String location);

    List<Establishment> findByScore(float score);

    List<Establishment> findByScoreBetween(float minScore, float maxScore);

    @Query(value = "SELECT SUM(total_score) FROM publications WHERE establishment_id = :id", nativeQuery = true)
    float sumScore(int id);

    @Query(value = "SELECT COUNT(id) FROM publications WHERE establishment_id = :id", nativeQuery = true)
    float countPublications(int id);
}
