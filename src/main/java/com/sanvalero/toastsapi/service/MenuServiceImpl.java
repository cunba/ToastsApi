package com.sanvalero.toastsapi.service;

import java.time.LocalDate;

import com.sanvalero.toastsapi.exception.NotFoundException;
import com.sanvalero.toastsapi.model.Menu;
import com.sanvalero.toastsapi.repository.MenuRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepository mr;

    @Override
    public Flux<Menu> findByDate(LocalDate date) {
        return mr.findByDate(date);
    }

    @Override
    public Flux<Menu> findByDateBetween(LocalDate minDate, LocalDate maxDate) {
        return mr.findByDateBetween(minDate, maxDate);
    }

    @Override
    public Flux<Menu> findByPrice(float price) {
        return mr.findByPrice(price);
    }

    @Override
    public Flux<Menu> findByPriceBetween(float minPrice, float maxPrice) {
        return mr.findByPriceBetween(minPrice, maxPrice);
    }

    @Override
    public Flux<Menu> findByPunctuation(float punctuation) {
        return mr.findByPunctuation(punctuation);
    }

    @Override
    public Flux<Menu> findByPunctuationBetween(float minPunctuation, float maxPunctuation) {
        return mr.findByPunctuationBetween(minPunctuation, maxPunctuation);
    }

    @Override
    public Mono<Menu> findById(int id) throws NotFoundException {
        return mr.findById(id).onErrorReturn(new Menu());
    }

    @Override
    public Flux<Menu> findAll() {
        return mr.findAll();
    }

    @Override
    public Mono<Menu> addMenu(Menu menu) {
        return mr.save(menu);
    }

    @Override
    public Mono<Menu> updateMenu(Menu menu) {
        return mr.save(menu);
    }

    @Override
    public void deleteMenu(Menu menu) {
        mr.delete(menu);
    }

    @Override
    public void deleteAll() {
        mr.deleteAll();
    }
}
