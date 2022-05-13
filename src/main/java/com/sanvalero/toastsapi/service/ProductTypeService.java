package com.sanvalero.toastsapi.service;

import java.util.UUID;
import java.util.Vector;

import com.sanvalero.toastsapi.exception.NotFoundException;
import com.sanvalero.toastsapi.model.ProductType;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductTypeService {
    Flux<ProductType> findByProductName(String name);

    Mono<ProductType> findByType(String type);

    Mono<ProductType> findByProductNameAndType(String name, String type);

    Flux<ProductType> findAll();

    Mono<ProductType> findById(UUID id) throws NotFoundException;

    Flux<ProductType> findByIds(Vector<UUID> ids);

    Mono<ProductType> addType(ProductType type);

    Mono<ProductType> updateType(ProductType type);

    void deleteType(ProductType type);

    void deleteAll();
}
