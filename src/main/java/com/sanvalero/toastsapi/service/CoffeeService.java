package com.sanvalero.toastsapi.service;

import java.util.List;

import com.sanvalero.toastsapi.exception.NotFoundException;
import com.sanvalero.toastsapi.model.Coffee;
import com.sanvalero.toastsapi.model.CoffeeType;
import com.sanvalero.toastsapi.model.dto.CoffeeDTO;

public interface CoffeeService extends ProductTemplateService<Coffee> {
    List<Coffee> findByType(CoffeeType coffeeType);

    List<Coffee> findByTypes(List<CoffeeType> coffeeTypeList);

    Coffee addCoffee(CoffeeDTO coffeeDTO) throws NotFoundException;

    Coffee deleteCoffee(int id) throws NotFoundException;

    Coffee modifyCoffee(Coffee coffee);
}
