package com.sanvalero.toastsapi.service;

import java.util.List;

import com.sanvalero.toastsapi.exception.NotFoundException;
import com.sanvalero.toastsapi.model.ProductType;
import com.sanvalero.toastsapi.repository.ProductTypeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductTypeServiceImpl implements ProductTypeService {

    @Autowired
    private ProductTypeRepository ptr;

    @Override
    public List<ProductType> findByProductName(String name) {
        return ptr.findByProductName(name);
    }

    @Override
    public ProductType findByType(String type) {
        return ptr.findByType(type);
    }

    @Override
    public ProductType findByProductNameAndType(String name, String type) {
        return ptr.findByProductNameAndType(name, type);
    }

    @Override
    public List<ProductType> findAll() {
        return ptr.findAll();
    }

    @Override
    public ProductType findById(int id) throws NotFoundException {
        return ptr.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public ProductType addType(ProductType type) {
        return ptr.save(type);
    }

    @Override
    public ProductType deleteType(ProductType type) {
        ptr.delete(type);
        return type;
    }

    @Override
    public ProductType modifyType(ProductType type) {
        return ptr.save(type);
    }

}
