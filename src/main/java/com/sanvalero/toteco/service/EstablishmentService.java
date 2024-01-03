package com.sanvalero.toteco.service;

import java.util.List;
import java.util.UUID;

import com.sanvalero.toteco.model.Establishment;

public interface EstablishmentService extends GlobalService<Establishment> {
    List<Establishment> findByName(String name);

    float sumScore(UUID id);

    float countPublications(UUID id);
}
