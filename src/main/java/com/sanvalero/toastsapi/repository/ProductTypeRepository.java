package com.sanvalero.toastsapi.repository;

import java.util.List;

import com.sanvalero.toastsapi.model.ProductType;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTypeRepository extends CrudRepository<ProductType, Integer> {
    List<ProductType> findAll();

    List<ProductType> findByName(String name);

    List<ProductType> findByType(String type);

    List<ProductType> findByNameAndType(String name, String type);
}
