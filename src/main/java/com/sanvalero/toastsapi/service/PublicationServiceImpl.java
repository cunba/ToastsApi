package com.sanvalero.toastsapi.service;

import java.time.LocalDate;
import java.util.List;

import com.sanvalero.toastsapi.exception.NotFoundException;
import com.sanvalero.toastsapi.model.Establishment;
import com.sanvalero.toastsapi.model.Publication;
import com.sanvalero.toastsapi.model.User;
import com.sanvalero.toastsapi.repository.PublicationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublicationServiceImpl implements PublicationService {

    @Autowired
    private PublicationRepository pr;

    @Override
    public List<Publication> findByDate(LocalDate date) {
        return pr.findByDate(date);
    }

    @Override
    public List<Publication> findByDateBetween(LocalDate minDate, LocalDate maxDate) {
        return pr.findByDateBetween(minDate, maxDate);
    }

    @Override
    public List<Publication> findByTotalPrice(float price) {
        return pr.findByTotalPrice(price);
    }

    @Override
    public List<Publication> findByTotalPriceBetween(float minPrice, float maxPrice) {
        return pr.findByTotalPriceBetween(minPrice, maxPrice);
    }

    @Override
    public List<Publication> findByTotalPunctuation(float punctuation) {
        return pr.findByTotalPunctuation(punctuation);
    }

    @Override
    public List<Publication> findByTotalPunctuationBetween(float minPunctuation, float maxPunctuation) {
        return pr.findByTotalPunctuationBetween(minPunctuation, maxPunctuation);
    }

    @Override
    public List<Publication> findByEstablishment(Establishment establishment) {
        return pr.findByEstablishment(establishment);
    }

    @Override
    public List<Publication> findByUser(User user) {
        return pr.findByUser(user);
    }

    @Override
    public List<Publication> findByProductType(String type) {
        return pr.findByProductType(type);
    }

    @Override
    public List<Publication> findAll() {
        return pr.findAll();
    }

    @Override
    public Publication findById(int id) throws NotFoundException {
        return pr.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public float totalPrice(int id) {
        return pr.totalPrice(id);
    }

    @Override
    public float totalPunctuation(int id) {
        return pr.totalPunctuation(id);
    }

    @Override
    public void updatePricePunctuation(Publication publication) {
        pr.save(publication);
    }

    @Override
    public Publication addPublication(Publication publication) {
        return pr.save(publication);
    }

    @Override
    public Publication updatePublication(Publication publication) {
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
