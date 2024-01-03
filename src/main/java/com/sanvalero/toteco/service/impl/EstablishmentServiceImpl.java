package com.sanvalero.toteco.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanvalero.toteco.exception.NotFoundException;
import com.sanvalero.toteco.model.Establishment;
import com.sanvalero.toteco.repository.EstablishmentRepository;
import com.sanvalero.toteco.service.EstablishmentService;

@Service
public class EstablishmentServiceImpl implements EstablishmentService {

    @Autowired
    private EstablishmentRepository er;

    @Override
    public List<Establishment> findByName(String name) {
        return er.findByName(name);
    }

    @Override
    public Establishment findById(UUID id) throws NotFoundException {
        return er.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<Establishment> findAll() {
        return er.findAll();
    }

    @Override
    public Establishment save(Establishment establishment) {
        return er.save(establishment);
    }

    @Override
    public void update(Establishment establishment) {
        er.save(establishment);
    }

    @Override
    public void delete(Establishment establishment) {
        er.delete(establishment);
    }

    @Override
    public void deleteAll() {
        er.deleteAll();
    }

    @Override
    public float sumScore(UUID id) {
        return er.sumScore(id);
    }

    @Override
    public float countPublications(UUID id) {
        return er.sumScore(id);
    }
}
