package com.sanvalero.toastsapi.service;

import java.time.LocalDate;
import java.util.List;

import com.sanvalero.toastsapi.model.Menu;
import com.sanvalero.toastsapi.model.Publication;
import com.sanvalero.toastsapi.model.Tea;
import com.sanvalero.toastsapi.model.TeaType;

import org.springframework.stereotype.Service;

@Service
public class TeaServiceImpl implements TeaService {

    @Override
    public List<Tea> findByType(TeaType teaType) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Tea> findByDate(LocalDate date) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Tea> findByDateBetween(LocalDate minDate, LocalDate maxDate) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Tea> findByPrice(float price) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Tea> findByPriceBetween(float minPrice, float maxPrice) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Tea> findByPunctuation(float punctuation) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Tea> findByPunctuationBetween(float minPunctuation, float maxPunctuation) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Tea> findByWithMenu(boolean withMenu) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Tea> findByMenu(Menu menu) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Tea> findByPublication(Publication publication) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Tea addTea(Tea tea) {
        // TODO Auto-generated method stub
        return null;
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
