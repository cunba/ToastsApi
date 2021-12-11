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
    public List<ProductType> findByName(String name) {
        return ptr.findByName(name);
    }

    @Override
    public List<ProductType> findByType(String type) {
        return ptr.findByType(type);
    }

    @Override
    public List<ProductType> findByNameAndType(String name, String type) {
        return ptr.findByNameAndType(name, type);
    }

    @Override
    public List<ProductType> findAllTypes() {
        return (List<ProductType>) ptr.findAll();
    }

    @Override
    public ProductType findById(int id) throws NotFoundException {
        return ptr.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public ProductType addType(ProductType type) throws NotFoundException {
        return ptr.save(type);
    }

    @Override
    public ProductType deleteType(int id) throws NotFoundException {
        ProductType type = ptr.findById(id).orElseThrow(NotFoundException::new);
        ptr.delete(type);
        return type;
    }

    @Override
    public ProductType modifyType(ProductType type) {
        if (ptr.existsById(type.getId())) {
            return ptr.save(type);
        }

        return null;
    }
    
}
