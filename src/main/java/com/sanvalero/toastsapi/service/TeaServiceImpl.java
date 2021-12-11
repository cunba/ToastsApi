package com.sanvalero.toastsapi.service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import com.sanvalero.toastsapi.exception.NotFoundException;
import com.sanvalero.toastsapi.model.Tea;
import com.sanvalero.toastsapi.model.TeaType;
import com.sanvalero.toastsapi.model.Menu;
import com.sanvalero.toastsapi.model.Publication;
import com.sanvalero.toastsapi.model.dto.TeaDTO;
import com.sanvalero.toastsapi.repository.TeaRepository;
import com.sanvalero.toastsapi.repository.TeaTypeRepository;
import com.sanvalero.toastsapi.repository.MenuRepository;
import com.sanvalero.toastsapi.repository.PublicationRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeaServiceImpl implements TeaService {

    @Autowired
    private TeaRepository tr;
    @Autowired
    private TeaTypeRepository ttr;
    @Autowired
    private MenuRepository mr;
    @Autowired
    private PublicationRepository pr;

    @Override
    public List<Tea> findByType(TeaType teaType) {
        return tr.findByType(teaType);
    }

    @Override
    public List<Tea> findByTypes(List<TeaType> teaTypeList) {
        List<Tea> teas = new LinkedList<>();
        for (TeaType teaType : teaTypeList) {
            List<Tea> lista = tr.findByType(teaType);
            for (Tea tea : lista) {
                teas.add(tea);
            }
        }
        return teas;
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
    public Tea findById(int id) throws NotFoundException {
        return tr.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<Tea> findAll() {
        return (List<Tea>) tr.findAll();
    }

    @Override
    public Tea addTea(TeaDTO teaDTO) throws NotFoundException {
        TeaType type = ttr.findById(teaDTO.getTypeId())
                .orElseThrow(NotFoundException::new);
        Menu menu = mr.findById(teaDTO.getMenuId())
                .orElseThrow(NotFoundException::new);
        Publication publication = pr.findById(teaDTO.getPublicationId())
                .orElseThrow(NotFoundException::new);

        ModelMapper mapper = new ModelMapper();
        Tea tea = mapper.map(teaDTO, Tea.class);
        tea.setType(type);
        tea.setMenu(menu);
        tea.setPublication(publication);

        return tr.save(tea);
    }

    @Override
    public Tea deleteTea(int id) throws NotFoundException {
        Tea tea = tr.findById(id).orElseThrow(NotFoundException::new);
        tr.delete(tea);
        return tea;
    }

    @Override
    public Tea modifyTea(Tea tea) {
        if (tr.existsById(tea.getId())) {
            return tr.save(tea);
        }

        return null;
    }

}
