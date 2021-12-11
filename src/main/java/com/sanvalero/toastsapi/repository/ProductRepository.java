package com.sanvalero.toastsapi.repository;

import java.time.LocalDate;
import java.util.List;

import com.sanvalero.toastsapi.model.Menu;
import com.sanvalero.toastsapi.model.Product;
import com.sanvalero.toastsapi.model.ProductType;
import com.sanvalero.toastsapi.model.Publication;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {
    List<Product> findAll();

    List<Product> findByType(ProductType productType);

    List<Product> findByDate(LocalDate date);

    List<Product> findByDateBetween(LocalDate minDate, LocalDate maxDate);

    List<Product> findByPrice(float price);

    List<Product> findByPriceBetween(float minPrice, float maxPrice);

    List<Product> findByPunctuation(float punctuation);

    List<Product> findByPunctuationBetween(float minPunctuation, float maxPunctuation);

    List<Product> findByInMenu(boolean inMenu);

    List<Product> findByMenu(Menu menu);

    List<Product> findByPublication(Publication publication);
}
