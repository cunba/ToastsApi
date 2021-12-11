package com.sanvalero.toastsapi.service;

import java.util.List;

import com.sanvalero.toastsapi.model.CoffeeType;
import com.sanvalero.toastsapi.repository.CoffeeTypeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoffeeTypeServiceImpl implements CoffeeTypeService {

    @Autowired
    CoffeeTypeRepository ctr;

    @Override
    public List<CoffeeType> findAllTypes() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CoffeeType findById(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CoffeeType addType(CoffeeType type) {
        return ctr.save(type);
    }

    @Override
    public CoffeeType deleteType(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CoffeeType modifyType(CoffeeType type, int id) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
