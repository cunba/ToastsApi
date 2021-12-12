package com.sanvalero.toastsapi.controller;

import java.util.List;

import com.sanvalero.toastsapi.exception.NotFoundException;
import com.sanvalero.toastsapi.model.Establishment;
import com.sanvalero.toastsapi.service.EstablishmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public class EstablishmentController {
    @Autowired
    private EstablishmentService es;

    @GetMapping("/establishments")
    public List<Establishment> getAllEstablishments() {
        return es.findAll();
    }

    @GetMapping("/establishment/id={id}")
    public Establishment getEstablishmentById(@PathVariable int id) throws NotFoundException {
        return es.findById(id);
    }

    @GetMapping("/establishment/name={name}")
    public Establishment gEstablishmentByName(@PathVariable String name) {
        return es.findByName(name);
    }
}
