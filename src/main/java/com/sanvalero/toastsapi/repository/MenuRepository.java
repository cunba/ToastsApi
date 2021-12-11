package com.sanvalero.toastsapi.repository;

import java.time.LocalDate;
import java.util.List;

import com.sanvalero.toastsapi.model.Menu;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends CrudRepository<Menu, Integer> {
    List<Menu> findAll();

    List<Menu> findByDate(LocalDate date);

    List<Menu> findByDateBetween(LocalDate minDate, LocalDate maxDate);

    List<Menu> findByPrice(float price);

    List<Menu> findByPriceBetween(float minPrice, float maxPrice);

    List<Menu> findByPunctuation(float punctuation);

    List<Menu> findByPunctuationBetween(float minPunctuation, float maxPunctuation);
}
