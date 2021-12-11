package com.sanvalero.toastsapi.service;

import java.util.List;

import com.sanvalero.toastsapi.exception.NotFoundException;
import com.sanvalero.toastsapi.model.ProductType;

public interface ProductTypeService {
    List<ProductType> findByName(String name);

    List<ProductType> findByType(String type);

    List<ProductType> findByNameAndType(String name, String type);

    List<ProductType> findAllTypes();

    ProductType findById(int id) throws NotFoundException;

    ProductType addType(ProductType type) throws NotFoundException;

    ProductType deleteType(int id) throws NotFoundException;

    ProductType modifyType(ProductType type);
}
