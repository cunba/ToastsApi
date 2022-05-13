package com.sanvalero.toastsapi.service;

import java.time.LocalDate;

import com.sanvalero.toastsapi.exception.NotFoundException;
import com.sanvalero.toastsapi.model.Menu;
import com.sanvalero.toastsapi.model.Product;
import com.sanvalero.toastsapi.model.ProductType;
import com.sanvalero.toastsapi.model.Publication;
import com.sanvalero.toastsapi.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository pr;

    @Override
    public Flux<Product> findByType(ProductType productType) {
        return pr.findByType(productType);
    }

    @Override
    public Flux<Product> findByDate(LocalDate date) {
        return pr.findByDate(date);
    }

    @Override
    public Flux<Product> findByDateBetween(LocalDate minDate, LocalDate maxDate) {
        return pr.findByDateBetween(minDate, maxDate);
    }

    @Override
    public Flux<Product> findByPrice(float price) {
        return pr.findByPrice(price);
    }

    @Override
    public Flux<Product> findByPriceBetween(float minPrice, float maxPrice) {
        return pr.findByPriceBetween(minPrice, maxPrice);
    }

    @Override
    public Flux<Product> findByPunctuation(float punctuation) {
        return pr.findByPunctuation(punctuation);
    }

    @Override
    public Flux<Product> findByPunctuationBetween(float minPunctuation, float maxPunctuation) {
        return pr.findByPunctuationBetween(minPunctuation, maxPunctuation);
    }

    @Override
    public Flux<Product> findByInMenu(boolean inMenu) {
        return pr.findByInMenu(inMenu);
    }

    @Override
    public Flux<Product> findByMenu(Menu menu) {
        return pr.findByMenu(menu);
    }

    @Override
    public Flux<Product> findByPublication(Publication publication) {
        return pr.findByPublication(publication);
    }

    @Override
    public Mono<Product> findById(String id) throws NotFoundException {
        return pr.findById(id).onErrorReturn(new Product());
    }

    @Override
    public Flux<Product> findAll() {
        return pr.findAll();
    }

    @Override
    public Mono<Product> addProduct(Product product) {
        return pr.insert(product);
    }

    @Override
    public Mono<Product> updateProduct(Product product) {
        return pr.save(product);
    }

    @Override
    public void updatePrice(Product product) {
        pr.save(product);
    }

    @Override
    public void updatePunctuation(Product product) {
        pr.save(product);
    }

    @Override
    public void deleteProduct(Product product) {
        pr.delete(product);
    }

    @Override
    public void deleteAll() {
        pr.deleteAll();
    }
}
