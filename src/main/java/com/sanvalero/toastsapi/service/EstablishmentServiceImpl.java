package com.sanvalero.toastsapi.service;

import java.time.LocalDate;
import java.util.List;

import com.sanvalero.toastsapi.model.Establishment;

import org.springframework.stereotype.Service;

@Service
public class EstablishmentServiceImpl implements EstablishmentService {

    @Override
    public List<Establishment> findByName(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Establishment> findByCreationDate(LocalDate date) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Establishment> findByCreationDateBetween(LocalDate minDate, LocalDate maxDate) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Establishment> findByOpen(boolean open) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Establishment> findByLocation(String location) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Establishment> findByPunctuation(float punctuation) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Establishment> findByPunctuationBetween(float minPunctuation, float maxPunctuation) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Establishment addEstablishment(Establishment establishment) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Establishment deleteEstablishment(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Establishment modifyEstablishment(Establishment establishment, int id) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
