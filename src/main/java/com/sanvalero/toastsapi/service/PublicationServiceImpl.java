package com.sanvalero.toastsapi.service;

import java.time.LocalDate;
import java.util.List;

import com.sanvalero.toastsapi.exception.NotFoundException;
import com.sanvalero.toastsapi.model.Establishment;
import com.sanvalero.toastsapi.model.Publication;
import com.sanvalero.toastsapi.model.User;
import com.sanvalero.toastsapi.model.dto.PublicationDTO;
import com.sanvalero.toastsapi.repository.EstablishmentRepository;
import com.sanvalero.toastsapi.repository.PublicationRepository;
import com.sanvalero.toastsapi.repository.UserRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublicationServiceImpl implements PublicationService {

    @Autowired
    private PublicationRepository pr;
    @Autowired
    private EstablishmentRepository er;
    @Autowired
    private UserRepository ur;

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
    public List<Publication> findAll() {
        return pr.findAll();
    }

    @Override
    public Publication findById(int id) throws NotFoundException {
        return pr.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public Publication addPublication(PublicationDTO publicationDTO) throws NotFoundException {
        Establishment establishment = er.findById(publicationDTO.getEstablishmentId())
            .orElseThrow(NotFoundException::new);
        User user = ur.findById(publicationDTO.getUserId())
            .orElseThrow(NotFoundException::new);

        ModelMapper mapper = new ModelMapper();
        Publication publication = mapper.map(publicationDTO, Publication.class);
        publication.setEstablishment(establishment);
        publication.setUser(user);

        return pr.save(publication);
    }

    @Override
    public Publication deletePublication(Publication publication) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Publication modifyPublication(Publication publication) {
        return pr.save(publication);
    }
    
}
