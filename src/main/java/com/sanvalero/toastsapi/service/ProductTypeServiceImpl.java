package com.sanvalero.toastsapi.service;

import java.util.Vector;

import com.sanvalero.toastsapi.exception.NotFoundException;
import com.sanvalero.toastsapi.model.ProductType;
import com.sanvalero.toastsapi.repository.ProductTypeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductTypeServiceImpl implements ProductTypeService {

    @Autowired
    private ProductTypeRepository ptr;

    @Override
    public Flux<ProductType> findByProductName(String name) {
        return ptr.findByProductName(name);
    }

    @Override
    public Mono<ProductType> findByType(String type) {
        return ptr.findByType(type);
    }

    @Override
    public Mono<ProductType> findByProductNameAndType(String name, String type) {
        return ptr.findByProductNameAndType(name, type);
    }

    @Override
    public Flux<ProductType> findAll() {
        return ptr.findAll();
    }

    @Override
    public Mono<ProductType> findById(int id) throws NotFoundException {
        return ptr.findById(id).onErrorReturn(new ProductType());
    }

    @Override
    public Flux<ProductType> findByIds(Vector<Integer> ids) {
        return (Flux<ProductType>) ptr.findAllById(ids);
    }

    @Override
    public Mono<ProductType> addType(ProductType type) {
        return ptr.save(type);
    }

    @Override
    public Mono<ProductType> updateType(ProductType type) {
        return ptr.save(type);
    }

    @Override
    public void deleteType(ProductType type) {
        ptr.delete(type);
    }

    @Override
    public void deleteAll() {
        ptr.deleteAll();
    }

}
