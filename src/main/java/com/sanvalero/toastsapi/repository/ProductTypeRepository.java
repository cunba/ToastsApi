package com.sanvalero.toastsapi.repository;

import java.util.List;

import com.sanvalero.toastsapi.model.ProductType;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTypeRepository extends CrudRepository<ProductType, Integer> {
    List<ProductType> findAll();

    List<ProductType> findByProductName(String name);

    ProductType findByType(String type);

    ProductType findByProductNameAndType(String name, String type);
}
