package com.sanvalero.toastsapi.service;

import java.time.LocalDate;
import java.util.List;

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
    private CoffeeRepository coffeeRepository;
    @Autowired
    private CoffeeTypeRepository coffeeTypeRepository;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private PublicationRepository publicationRepository;

    @Override
    public List<Coffee> findByType(CoffeeType coffeeType) {
        return coffeeRepository.findByType(coffeeType);
    }

    @Override
    public List<Coffee> findByDate(LocalDate date) {
        return coffeeRepository.findByDate(date);
    }

    @Override
    public List<Coffee> findByDateBetween(LocalDate minDate, LocalDate maxDate) {
        return coffeeRepository.findByDateBetween(minDate, maxDate);
    }

    @Override
    public List<Coffee> findByPrice(float price) {
        return coffeeRepository.findByPrice(price);
    }

    @Override
    public List<Coffee> findByPriceBetween(float minPrice, float maxPrice) {
        return coffeeRepository.findByPriceBetween(minPrice, maxPrice);
    }

    @Override
    public List<Coffee> findByPunctuation(float punctuation) {
        return coffeeRepository.findByPunctuation(punctuation);
    }

    @Override
    public List<Coffee> findByPunctuationBetween(float minPunctuation, float maxPunctuation) {
        return coffeeRepository.findByPunctuationBetween(minPunctuation, maxPunctuation);
    }

    @Override
    public List<Coffee> findByWithMenu(boolean withMenu) {
        return coffeeRepository.findByWithMenu(withMenu);
    }

    @Override
    public List<Coffee> findByMenu(Menu menu) {
        return coffeeRepository.findByMenu(menu);
    }

    @Override
    public List<Coffee> findByPublication(Publication publication) {
        return coffeeRepository.findByPublication(publication);
    }

    @Override
    public Coffee addCoffee(CoffeeDTO coffeeDTO) {
        // CoffeeType type = coffeeTypeRepository.findById(coffeeDTO.getTypeId());
        // Menu menu = menuRepository.findById(coffeeDTO.getMenuId());
        // Publication publication = publicationRepository.findById(coffeeDTO.getPublicationId());

        // ModelMapper mapper = new ModelMapper();
        // Coffee coffee = mapper.map(coffeeDTO, Coffee.class);
        // coffee.setType(type);
        // coffee.setMenu(menu);
        // coffee.setPublication(publication);

        // return coffeeRepository.save(coffee);

        return null;
    }

    @Override
    public Coffee deleteCoffee(int id) {
        // Coffee coffee = coffeeRepository.findById(id);
        // coffeeRepository.delete(coffee);
        // return coffee;
        return null;
    }

    @Override
    public Coffee modifyCoffee(CoffeeDTO coffeeDTO, int id) {
        return null;
    }

    @Override
    public List<Coffee> findAllCoffees() {
        return findAllCoffees();
    }

    @Override
    public Coffee findById(int id) {
        return findById(id);
    }
    
}
