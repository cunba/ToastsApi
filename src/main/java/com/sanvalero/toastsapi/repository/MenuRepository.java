package com.sanvalero.toastsapi.repository;

import java.time.LocalDate;
import java.util.List;

import com.sanvalero.toastsapi.model.Menu;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends CrudRepository<Menu, Integer> {
    List<Menu> findByDate(LocalDate date);

    // List<Menu> findByDate(LocalDate minDate, LocalDate maxDate);

    List<Menu> findByPrice(float price);

    // List<Menu> findByPrice(float minPrice, float maxPrice);

    List<Menu> findByPunctuation(float punctuation);

    // List<Menu> findByPunctuation(float minPunctuation, float maxPunctuation);
}
