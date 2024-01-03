package com.sanvalero.toteco.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanvalero.toteco.exception.NotFoundException;
import com.sanvalero.toteco.model.Establishment;
import com.sanvalero.toteco.model.Publication;
import com.sanvalero.toteco.model.UserModel;
import com.sanvalero.toteco.repository.PublicationRepository;
import com.sanvalero.toteco.service.PublicationService;

@Service
public class PublicationServiceImpl implements PublicationService {

    @Autowired
    private PublicationRepository pr;

    @Override
    public List<Publication> findByEstablishment(Establishment establishment) {
        return pr.findByEstablishment(establishment);
    }

    @Override
    public List<Publication> findByUser(UserModel user) {
        return pr.findByUser(user);
    }

    @Override
    public List<Publication> findAll() {
        return pr.findAll();
    }

    @Override
    public Publication findById(UUID id) throws NotFoundException {
        return pr.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public Publication save(Publication publication) {
        return pr.save(publication);
    }

    @Override
    public void update(Publication publication) {
        pr.save(publication);
    }

    @Override
    public void delete(Publication publication) {
        pr.delete(publication);
    }

    @Override
    public void deleteAll() {
        pr.deleteAll();
    }

}
