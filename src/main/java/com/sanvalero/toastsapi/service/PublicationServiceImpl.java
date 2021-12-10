package com.sanvalero.toastsapi.service;

import java.time.LocalDate;
import java.util.List;

import com.sanvalero.toastsapi.model.Establishment;
import com.sanvalero.toastsapi.model.Publication;
import com.sanvalero.toastsapi.model.User;

import org.springframework.stereotype.Service;

@Service
public class PublicationServiceImpl implements PublicationService {

    @Override
    public List<Publication> findByDate(LocalDate date) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Publication> findByDateBetween(LocalDate minDate, LocalDate maxDate) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Publication> findByTotalPrice(float price) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Publication> findByTotalPriceBetween(float minPrice, float maxPrice) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Publication> findByTotalPunctuation(float punctuation) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Publication> findByTotalPunctuationBetween(float minPunctuation, float maxPunctuation) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Publication> findByEstablishment(Establishment establishment) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Publication> findByUser(User user) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Publication> findAllPublications() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Publication findById(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Publication addPublication(Publication publication) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Publication deletePublication(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Publication modifyPublication(Publication publication, int id) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
