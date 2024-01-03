package com.sanvalero.toteco.service;

import java.util.List;
import java.util.UUID;

import com.sanvalero.toteco.exception.NotFoundException;

public interface GlobalService<T> {
    T findById(UUID id) throws NotFoundException;

    List<T> findAll();

    T save(T t);

    void update(T t);

    void delete(T t);

    void deleteAll();
}
