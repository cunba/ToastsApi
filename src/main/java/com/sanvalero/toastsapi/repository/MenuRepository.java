package com.sanvalero.toastsapi.repository;

import java.time.LocalDate;
import java.util.List;

import com.sanvalero.toastsapi.model.Menu;

import org.springframework.data.repository.CrudRepository;

public interface MenuRepository extends CrudRepository<Menu, Integer> {
    List<Menu> findByDate(LocalDate date);

    List<Menu> findByDateRange(LocalDate minDate, LocalDate maxDate);

    List<Menu> findByPrice(float price);

    List<Menu> findByPriceRange(float minPrice, float maxPrice);

    List<Menu> findByPunctuation(float punctuation);

    List<Menu> findByPunctuationRange(float minPunctuation, float maxPunctuation);
}
