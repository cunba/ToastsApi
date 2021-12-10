package com.sanvalero.toastsapi.service;

import java.time.LocalDate;
import java.util.List;

import com.sanvalero.toastsapi.model.Menu;

import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl implements MenuService {

    @Override
    public List<Menu> findAllMenus() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Menu> findByDate(LocalDate date) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Menu> findByDateBetween(LocalDate minDate, LocalDate maxDate) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Menu> findByPrice(float price) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Menu> findByPriceBetween(float minPrice, float maxPrice) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Menu> findByPunctuation(float punctuation) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Menu> findByPunctuationBetween(float minPunctuation, float maxPunctuation) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Menu findById(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Menu addMenu(Menu menu) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Menu deleteMenu(Menu menu) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Menu modifyMenu(Menu menu, int id) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
