package com.sanvalero.toastsapi.service;

import java.time.LocalDate;
import java.util.List;

import com.sanvalero.toastsapi.model.Menu;
import com.sanvalero.toastsapi.model.Publication;
import com.sanvalero.toastsapi.model.Toast;
import com.sanvalero.toastsapi.model.ToastType;

import org.springframework.stereotype.Service;

@Service
public class ToastServiceImpl implements ToastService {

    @Override
    public List<Toast> findByType(ToastType toastType) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Toast> findByDate(LocalDate date) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Toast> findByDateBetween(LocalDate minDate, LocalDate maxDate) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Toast> findByPrice(float price) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Toast> findByPriceBetween(float minPrice, float maxPrice) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Toast> findByPunctuation(float punctuation) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Toast> findByPunctuationBetween(float minPunctuation, float maxPunctuation) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Toast> findByWithMenu(boolean withMenu) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Toast> findByMenu(Menu menu) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Toast> findByPublication(Publication publication) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Toast addToast(Toast toast) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Toast deleteToast(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Toast modifyToast(Toast oast, int id) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
