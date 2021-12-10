package com.sanvalero.toastsapi.service;

import java.time.LocalDate;
import java.util.List;

import com.sanvalero.toastsapi.model.Tea;
import com.sanvalero.toastsapi.model.TeaType;
import com.sanvalero.toastsapi.model.Menu;
import com.sanvalero.toastsapi.model.Publication;

public interface TeaService {
    List<Tea> findByType(TeaType teaType);

    List<Tea> findByDate(LocalDate date);

    List<Tea> findByDateBetween(LocalDate minDate, LocalDate maxDate);

    List<Tea> findByPrice(float price);

    List<Tea> findByPriceBetween(float minPrice, float maxPrice);

    List<Tea> findByPunctuation(float punctuation);

    List<Tea> findByPunctuationBetween(float minPunctuation, float maxPunctuation);

    List<Tea> findByWithMenu(boolean withMenu);

    List<Tea> findByMenu(Menu menu);

    List<Tea> findByPublication(Publication publication);

    Tea addTea(Tea tea);

    Tea deleteTea(int id);

    Tea modifyTea(Tea tea, int id);
}
