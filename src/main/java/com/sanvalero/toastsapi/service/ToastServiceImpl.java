package com.sanvalero.toastsapi.service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import com.sanvalero.toastsapi.exception.NotFoundException;
import com.sanvalero.toastsapi.model.Toast;
import com.sanvalero.toastsapi.model.ToastType;
import com.sanvalero.toastsapi.model.Menu;
import com.sanvalero.toastsapi.model.Publication;
import com.sanvalero.toastsapi.model.dto.ToastDTO;
import com.sanvalero.toastsapi.repository.ToastRepository;
import com.sanvalero.toastsapi.repository.ToastTypeRepository;
import com.sanvalero.toastsapi.repository.MenuRepository;
import com.sanvalero.toastsapi.repository.PublicationRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ToastServiceImpl implements ToastService {

    @Autowired
    private ToastRepository tr;
    @Autowired
    private ToastTypeRepository ttr;
    @Autowired
    private MenuRepository mr;
    @Autowired
    private PublicationRepository pr;

    @Override
    public List<Toast> findByType(ToastType toastType) {
        return tr.findByType(toastType);
    }

    @Override
    public List<Toast> findByTypes(List<ToastType> toastTypeList) {
        List<Toast> toasts = new LinkedList<>();
        for (ToastType toastType : toastTypeList) {
            List<Toast> lista = tr.findByType(toastType);
            for (Toast toast : lista) {
                toasts.add(toast);
            }
        }
        return toasts;
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
    public Toast findById(int id) throws NotFoundException {
        return tr.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<Toast> findAll() {
        return (List<Toast>) tr.findAll();
    }

    @Override
    public Toast addToast(ToastDTO toastDTO) throws NotFoundException {
        ToastType type = ttr.findById(toastDTO.getTypeId())
                .orElseThrow(NotFoundException::new);
        Menu menu = mr.findById(toastDTO.getMenuId())
                .orElseThrow(NotFoundException::new);
        Publication publication = pr.findById(toastDTO.getPublicationId())
                .orElseThrow(NotFoundException::new);

        ModelMapper mapper = new ModelMapper();
        Toast toast = mapper.map(toastDTO, Toast.class);
        toast.setType(type);
        toast.setMenu(menu);
        toast.setPublication(publication);

        return tr.save(toast);
    }

    @Override
    public Toast deleteToast(int id) throws NotFoundException {
        Toast toast = tr.findById(id).orElseThrow(NotFoundException::new);
        tr.delete(toast);
        return toast;
    }

    @Override
    public Toast modifyToast(Toast toast) {
        if (tr.existsById(toast.getId())) {
            return tr.save(toast);
        }

        return null;
    }

}
