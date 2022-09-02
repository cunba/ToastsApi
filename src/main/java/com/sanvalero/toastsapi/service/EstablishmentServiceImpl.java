package com.sanvalero.toastsapi.service;

import java.time.LocalDate;
import java.util.List;

import com.sanvalero.toastsapi.exception.NotFoundException;
import com.sanvalero.toastsapi.model.Establishment;
import com.sanvalero.toastsapi.repository.EstablishmentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstablishmentServiceImpl implements EstablishmentService {

    @Autowired
    private EstablishmentRepository er;

    @Override
    public Establishment findByName(String name) {
        return er.findByName(name);
    }

    @Override
    public List<Establishment> findByCreationDate(LocalDate date) {
        return er.findByCreationDate(date);
    }

    @Override
    public List<Establishment> findByCreationDateBetween(LocalDate minDate, LocalDate maxDate) {
        return er.findByCreationDateBetween(minDate, maxDate);
    }

    @Override
    public List<Establishment> findByOpen(boolean open) {
        return er.findByOpen(open);
    }

    @Override
    public List<Establishment> findByLocation(String location) {
        return er.findByLocation(location);
    }

    @Override
    public List<Establishment> findByPunctuation(float punctuation) {
        return er.findByPunctuation(punctuation);
    }

    @Override
    public List<Establishment> findByPunctuationBetween(float minPunctuation, float maxPunctuation) {
        return er.findByPunctuationBetween(minPunctuation, maxPunctuation);
    }

    @Override
    public Establishment findById(int id) throws NotFoundException {
        return er.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<Establishment> findAll() {
        return er.findAll();
    }

    @Override
    public Establishment addEstablishment(Establishment establishment) {
        return er.save(establishment);
    }

    @Override
    public Establishment updateEstablishment(Establishment establishment) {
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

    @Override
    public float sumPunctuation(int id) {
        return er.sumPunctuation(id);
    }

    @Override
    public float countPublications(int id) {
        return er.sumPunctuation(id);
    }

    @Override
    public void updatePunctuation(Establishment establishment) {
        er.save(establishment);
    }
}
