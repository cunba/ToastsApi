package com.sanvalero.toastsapi.repository;

import java.time.LocalDate;
import java.util.List;

import com.sanvalero.toastsapi.model.Menu;
import com.sanvalero.toastsapi.model.Publication;
import com.sanvalero.toastsapi.model.Toast;
import com.sanvalero.toastsapi.model.ToastType;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToastRepository extends CrudRepository<Toast, Integer> {
    List<Toast> findByType(ToastType toastType);

    List<Toast> findByDate(LocalDate date);

    List<Toast> findByDateBetween(LocalDate minDate, LocalDate maxDate);

    List<Toast> findByPrice(float price);

    List<Toast> findByPriceBetween(float minPrice, float maxPrice);

    List<Toast> findByPunctuation(float punctuation);

    List<Toast> findByPunctuationBetween(float minPunctuation, float maxPunctuation);

    List<Toast> findByWithMenu(boolean withMenu);

    List<Toast> findByMenu(Menu menu);

    List<Toast> findByPublication(Publication publication);
}
