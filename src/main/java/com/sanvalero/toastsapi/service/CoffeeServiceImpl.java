package com.sanvalero.toastsapi.service;

import java.time.LocalDate;
import java.util.List;

import com.sanvalero.toastsapi.model.Coffee;
import com.sanvalero.toastsapi.model.CoffeeType;
import com.sanvalero.toastsapi.model.Menu;
import com.sanvalero.toastsapi.model.Publication;

import org.springframework.stereotype.Service;

@Service
public class CoffeeServiceImpl implements CoffeeService {

    @Override
    public List<Coffee> findByType(CoffeeType coffeeType) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Coffee> findByDate(LocalDate date) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Coffee> findByDateBetween(LocalDate minDate, LocalDate maxDate) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Coffee> findByPrice(float price) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Coffee> findByPriceBetween(float minPrice, float maxPrice) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Coffee> findByPunctuation(float punctuation) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Coffee> findByPunctuationBetween(float minPunctuation, float maxPunctuation) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Coffee> findByWithMenu(boolean withMenu) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Coffee> findByMenu(Menu menu) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Coffee> findByPublication(Publication publication) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Coffee addCoffee(Coffee coffee) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Coffee deleteCoffee(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Coffee modifyCoffee(Coffee coffee, int id) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
