package com.sanvalero.toastsapi.service;

import java.time.LocalDate;

import com.sanvalero.toastsapi.exception.NotFoundException;
import com.sanvalero.toastsapi.model.Menu;
import com.sanvalero.toastsapi.model.Product;
import com.sanvalero.toastsapi.model.ProductType;
import com.sanvalero.toastsapi.model.Publication;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {
    Flux<Product> findByDate(LocalDate date);

    Flux<Product> findByDateBetween(LocalDate minDate, LocalDate maxDate);

    Flux<Product> findByPrice(float price);

    Flux<Product> findByPriceBetween(float minPrice, float maxPrice);

    Flux<Product> findByPunctuation(float punctuation);

    Flux<Product> findByPunctuationBetween(float minPunctuation, float maxPunctuation);

    Flux<Product> findByInMenu(boolean inMenu);

    Flux<Product> findByMenu(Menu menu);

    Flux<Product> findByPublication(Publication publication);

    Flux<Product> findAll();

    Mono<Product> findById(int id) throws NotFoundException;

    Flux<Product> findByType(ProductType productType);

    Mono<Product> addProduct(Product product);

    Mono<Product> updateProduct(Product product);

    void updatePrice(Product product);

    void updatePunctuation(Product product);

    void deleteProduct(Product product);

    void deleteAll();
}
