package com.sanvalero.toastsapi.service;

import java.time.LocalDate;
import java.util.List;

import com.sanvalero.toastsapi.model.Menu;
import com.sanvalero.toastsapi.model.Publication;
import com.sanvalero.toastsapi.model.Tea;
import com.sanvalero.toastsapi.model.TeaType;
import com.sanvalero.toastsapi.repository.TeaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeaServiceImpl implements TeaService {

    @Autowired
    private TeaRepository tr;

    @Override
    public List<Tea> findByType(TeaType teaType) {
        return tr.findByType(teaType);
    }

    @Override
    public List<Tea> findByDate(LocalDate date) {
        return tr.findByDate(date);
    }

    @Override
    public List<Tea> findByDateBetween(LocalDate minDate, LocalDate maxDate) {
        return tr.findByDateBetween(minDate, maxDate);
    }

    @Override
    public List<Tea> findByPrice(float price) {
        return tr.findByPrice(price);
    }

    @Override
    public List<Tea> findByPriceBetween(float minPrice, float maxPrice) {
        return tr.findByPriceBetween(minPrice, maxPrice);
    }

    @Override
    public List<Tea> findByPunctuation(float punctuation) {
        return tr.findByPunctuation(punctuation);
    }

    @Override
    public List<Tea> findByPunctuationBetween(float minPunctuation, float maxPunctuation) {
        return tr.findByPunctuationBetween(minPunctuation, maxPunctuation);
    }

    @Override
    public List<Tea> findByWithMenu(boolean withMenu) {
        return tr.findByWithMenu(withMenu);
    }

    @Override
    public List<Tea> findByMenu(Menu menu) {
        return tr.findByMenu(menu);
    }

    @Override
    public List<Tea> findByPublication(Publication publication) {
        return tr.findByPublication(publication);
    }

    @Override
    public Tea addTea(Tea tea) {
        return tr.save(tea);
    }

    @Override
    public Tea deleteTea(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Tea modifyTea(Tea tea, int id) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
