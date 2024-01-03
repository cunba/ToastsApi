package com.sanvalero.toteco.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sanvalero.toteco.model.Establishment;

@Repository
public interface EstablishmentRepository extends CrudRepository<Establishment, UUID> {
    List<Establishment> findAll();

    List<Establishment> findByName(String name);

    @Query(value = "SELECT SUM(total_score) FROM publications WHERE establishment_id = :id", nativeQuery = true)
    float sumScore(UUID id);

    @Query(value = "SELECT COUNT(id) FROM publications WHERE establishment_id = :id", nativeQuery = true)
    float countPublications(UUID id);
}
