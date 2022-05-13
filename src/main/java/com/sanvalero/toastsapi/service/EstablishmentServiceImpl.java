package com.sanvalero.toastsapi.service;

import java.time.LocalDate;
import java.util.UUID;

import com.sanvalero.toastsapi.exception.NotFoundException;
import com.sanvalero.toastsapi.model.Establishment;
import com.sanvalero.toastsapi.repository.EstablishmentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EstablishmentServiceImpl implements EstablishmentService {

    @Autowired
    private EstablishmentRepository er;

    @Override
    public Mono<Establishment> findByName(String name) {
        return er.findByName(name);
    }

    @Override
    public Flux<Establishment> findByCreationDate(LocalDate date) {
        return er.findByCreationDate(date);
    }

    @Override
    public Flux<Establishment> findByCreationDateBetween(LocalDate minDate, LocalDate maxDate) {
        return er.findByCreationDateBetween(minDate, maxDate);
    }

    @Override
    public Flux<Establishment> findByOpen(boolean open) {
        return er.findByOpen(open);
    }

    @Override
    public Flux<Establishment> findByLocation(String location) {
        return er.findByLocation(location);
    }

    @Override
    public Flux<Establishment> findByPunctuation(float punctuation) {
        return er.findByPunctuation(punctuation);
    }

    @Override
    public Flux<Establishment> findByPunctuationBetween(float minPunctuation, float maxPunctuation) {
        return er.findByPunctuationBetween(minPunctuation, maxPunctuation);
    }

    @Override
    public Mono<Establishment> findById(UUID id) throws NotFoundException {
        return er.findById(id).onErrorReturn(new Establishment());
    }

    @Override
    public Flux<Establishment> findAll() {
        return er.findAll();
    }

    @Override
    public Mono<Establishment> addEstablishment(Establishment establishment) {
        return er.insert(establishment);
    }

    @Override
    public Mono<Establishment> updateEstablishment(Establishment establishment) {
        return er.save(establishment);
    }

    @Override
    public void deleteEstablishment(Establishment establishment) {
        er.delete(establishment);
    }

    @Override
    public void deleteAll() {
        er.deleteAll();
    }

    // @Override
    // public float sumPunctuation(String id) {
    // return er.sumPunctuation(id);
    // }

    @Override
    public void updatePunctuation(Establishment establishment) {
        er.save(establishment);
    }
}
