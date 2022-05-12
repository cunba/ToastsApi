package com.sanvalero.toastsapi.service;

import java.time.LocalDate;

import com.sanvalero.toastsapi.exception.NotFoundException;
import com.sanvalero.toastsapi.model.Establishment;
import com.sanvalero.toastsapi.model.Publication;
import com.sanvalero.toastsapi.model.UserModel;
import com.sanvalero.toastsapi.repository.PublicationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PublicationServiceImpl implements PublicationService {

    @Autowired
    private PublicationRepository pr;

    @Override
    public Flux<Publication> findByDate(LocalDate date) {
        return pr.findByDate(date);
    }

    @Override
    public Flux<Publication> findByDateBetween(LocalDate minDate, LocalDate maxDate) {
        return pr.findByDateBetween(minDate, maxDate);
    }

    @Override
    public Flux<Publication> findByTotalPrice(float price) {
        return pr.findByTotalPrice(price);
    }

    @Override
    public Flux<Publication> findByTotalPriceBetween(float minPrice, float maxPrice) {
        return pr.findByTotalPriceBetween(minPrice, maxPrice);
    }

    @Override
    public Flux<Publication> findByTotalPunctuation(float punctuation) {
        return pr.findByTotalPunctuation(punctuation);
    }

    @Override
    public Flux<Publication> findByTotalPunctuationBetween(float minPunctuation, float maxPunctuation) {
        return pr.findByTotalPunctuationBetween(minPunctuation, maxPunctuation);
    }

    @Override
    public Flux<Publication> findByEstablishment(Establishment establishment) {
        return pr.findByEstablishment(establishment);
    }

    @Override
    public Flux<Publication> findByUser(UserModel user) {
        return pr.findByUser(user);
    }

    // @Override
    // public Flux<Publication> findByProductType(String type) {
    //     return pr.findByProductType(type);
    // }

    @Override
    public Flux<Publication> findByDateBetweenAndTotalPriceBetweenAndTotalPunctuationBetween(LocalDate minDate,
            LocalDate maxDate, float minPrice, float maxPrice, float minPunctuation, float maxPunctuation) {

        return pr.findByDateBetweenAndTotalPriceBetweenAndTotalPunctuationBetween(minDate, maxDate, minPrice, maxPrice,
                minPunctuation, maxPunctuation);
    }

    @Override
    public Flux<Publication> findAll() {
        return pr.findAll();
    }

    @Override
    public Mono<Publication> findById(String id) throws NotFoundException {
        return pr.findById(id).onErrorReturn(new Publication());
    }

    // @Override
    // public float totalPrice(String id) {
    //     return pr.totalPrice(id);
    // }

    // @Override
    // public float totalPunctuation(String id) {
    //     return pr.totalPunctuation(id);
    // }

    @Override
    public void updatePricePunctuation(Publication publication) {
        pr.save(publication);
    }

    @Override
    public Mono<Publication> addPublication(Publication publication) {
        return pr.save(publication);
    }

    @Override
    public Mono<Publication> updatePublication(Publication publication) {
        return pr.save(publication);
    }

    @Override
    public void deletePublication(Publication publication) {
        pr.delete(publication);
    }

    @Override
    public void deleteAll() {
        pr.deleteAll();
    }

}
