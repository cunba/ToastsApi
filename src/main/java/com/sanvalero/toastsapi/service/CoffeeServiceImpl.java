package com.sanvalero.toastsapi.service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import com.sanvalero.toastsapi.exception.NotFoundException;
import com.sanvalero.toastsapi.model.Coffee;
import com.sanvalero.toastsapi.model.CoffeeType;
import com.sanvalero.toastsapi.model.Menu;
import com.sanvalero.toastsapi.model.Publication;
import com.sanvalero.toastsapi.model.dto.CoffeeDTO;
import com.sanvalero.toastsapi.repository.CoffeeRepository;
import com.sanvalero.toastsapi.repository.CoffeeTypeRepository;
import com.sanvalero.toastsapi.repository.MenuRepository;
import com.sanvalero.toastsapi.repository.PublicationRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoffeeServiceImpl implements CoffeeService {

    @Autowired
    private CoffeeRepository cr;
    @Autowired
    private CoffeeTypeRepository ctr;
    @Autowired
    private MenuRepository mr;
    @Autowired
    private PublicationRepository pr;

    @Override
    public List<Coffee> findByType(CoffeeType coffeeType) {
        return cr.findByType(coffeeType);
    }

    @Override
    public List<Coffee> findByTypes(List<CoffeeType> coffeeTypeList) {
        List<Coffee> coffees = new LinkedList<>();
        for (CoffeeType coffeeType : coffeeTypeList) {
            List<Coffee> lista = cr.findByType(coffeeType);
            for (Coffee coffee : lista) {
                coffees.add(coffee);
            }
        }
        return coffees;
    }

    @Override
    public List<Coffee> findByDate(LocalDate date) {
        return cr.findByDate(date);
    }

    @Override
    public List<Coffee> findByDateBetween(LocalDate minDate, LocalDate maxDate) {
        return cr.findByDateBetween(minDate, maxDate);
    }

    @Override
    public List<Coffee> findByPrice(float price) {
        return cr.findByPrice(price);
    }

    @Override
    public List<Coffee> findByPriceBetween(float minPrice, float maxPrice) {
        return cr.findByPriceBetween(minPrice, maxPrice);
    }

    @Override
    public List<Coffee> findByPunctuation(float punctuation) {
        return cr.findByPunctuation(punctuation);
    }

    @Override
    public List<Coffee> findByPunctuationBetween(float minPunctuation, float maxPunctuation) {
        return cr.findByPunctuationBetween(minPunctuation, maxPunctuation);
    }

    @Override
    public List<Coffee> findByWithMenu(boolean withMenu) {
        return cr.findByWithMenu(withMenu);
    }

    @Override
    public List<Coffee> findByMenu(Menu menu) {
        return cr.findByMenu(menu);
    }

    @Override
    public List<Coffee> findByPublication(Publication publication) {
        return cr.findByPublication(publication);
    }

    @Override
    public Coffee findById(int id) throws NotFoundException {
        return cr.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<Coffee> findAll() {
        return (List<Coffee>) cr.findAll();
    }

    @Override
    public Coffee addCoffee(CoffeeDTO coffeeDTO) throws NotFoundException {
        CoffeeType type = ctr.findById(coffeeDTO.getTypeId())
                .orElseThrow(NotFoundException::new);
        Menu menu = mr.findById(coffeeDTO.getMenuId())
                .orElseThrow(NotFoundException::new);
        Publication publication = pr.findById(coffeeDTO.getPublicationId())
                .orElseThrow(NotFoundException::new);

        ModelMapper mapper = new ModelMapper();
        Coffee coffee = mapper.map(coffeeDTO, Coffee.class);
        coffee.setType(type);
        coffee.setMenu(menu);
        coffee.setPublication(publication);

        return cr.save(coffee);
    }

    @Override
    public Coffee deleteCoffee(int id) throws NotFoundException {
        Coffee coffee = cr.findById(id).orElseThrow(NotFoundException::new);
        cr.delete(coffee);
        return coffee;
    }

    @Override
    public Coffee modifyCoffee(Coffee coffee) {
        if (cr.existsById(coffee.getId())) {
            return cr.save(coffee);
        }

        return null;
    }

}
