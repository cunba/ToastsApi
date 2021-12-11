package com.sanvalero.toastsapi.service;

import java.time.LocalDate;
import java.util.List;

import com.sanvalero.toastsapi.model.Menu;
import com.sanvalero.toastsapi.model.Publication;
import com.sanvalero.toastsapi.model.Toast;
import com.sanvalero.toastsapi.model.ToastType;
import com.sanvalero.toastsapi.repository.ToastRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ToastServiceImpl implements ToastService {

    @Autowired
    private ToastRepository tr;

    @Override
    public List<Toast> findByType(ToastType toastType) {
        return tr.findByType(toastType);
    }

    @Override
    public List<Toast> findByDate(LocalDate date) {
        return tr.findByDate(date);
    }

    @Override
    public List<Toast> findByDateBetween(LocalDate minDate, LocalDate maxDate) {
        return tr.findByDateBetween(minDate, maxDate);
    }

    @Override
    public List<Toast> findByPrice(float price) {
        return tr.findByPrice(price);
    }

    @Override
    public List<Toast> findByPriceBetween(float minPrice, float maxPrice) {
        return tr.findByPriceBetween(minPrice, maxPrice);
    }

    @Override
    public List<Toast> findByPunctuation(float punctuation) {
        return tr.findByPunctuation(punctuation);
    }

    @Override
    public List<Toast> findByPunctuationBetween(float minPunctuation, float maxPunctuation) {
        return tr.findByPunctuationBetween(minPunctuation, maxPunctuation);
    }

    @Override
    public List<Toast> findByWithMenu(boolean withMenu) {
        return tr.findByWithMenu(withMenu);
    }

    @Override
    public List<Toast> findByMenu(Menu menu) {
        return tr.findByMenu(menu);
    }

    @Override
    public List<Toast> findByPublication(Publication publication) {
        return tr.findByPublication(publication);
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
