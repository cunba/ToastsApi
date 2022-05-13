package com.sanvalero.toastsapi.repository;

import com.sanvalero.toastsapi.model.ProductType;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProductTypeRepository extends ReactiveMongoRepository<ProductType, String> {
    Flux<ProductType> findAll();

    Flux<ProductType> findByProductName(String name);

    Mono<ProductType> findByType(String type);

    Mono<ProductType> findByProductNameAndType(String name, String type);
}
