package com.sanvalero.toastsapi.repository;

import java.time.LocalDate;

import com.sanvalero.toastsapi.model.Menu;
import com.sanvalero.toastsapi.model.Product;
import com.sanvalero.toastsapi.model.ProductType;
import com.sanvalero.toastsapi.model.Publication;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product, Integer> {
    Flux<Product> findAll();

    Flux<Product> findByType(ProductType productType);

    Flux<Product> findByDate(LocalDate date);

    Flux<Product> findByDateBetween(LocalDate minDate, LocalDate maxDate);

    Flux<Product> findByPrice(float price);

    Flux<Product> findByPriceBetween(float minPrice, float maxPrice);

    Flux<Product> findByPunctuation(float punctuation);

    Flux<Product> findByPunctuationBetween(float minPunctuation, float maxPunctuation);

    Flux<Product> findByInMenu(boolean inMenu);

    Flux<Product> findByMenu(Menu menu);

    Flux<Product> findByPublication(Publication publication);
}
