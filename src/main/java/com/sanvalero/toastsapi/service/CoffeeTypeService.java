package com.sanvalero.toastsapi.service;

import java.util.List;

import com.sanvalero.toastsapi.model.CoffeeType;

public interface CoffeeTypeService {
    List<CoffeeType> findAllTypes();

    CoffeeType findById(int id);

    CoffeeType addType(CoffeeType type);

    CoffeeType deleteType(int id);

    CoffeeType modifyType(CoffeeType type, int id);
}
