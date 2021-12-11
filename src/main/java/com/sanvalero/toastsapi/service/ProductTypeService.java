package com.sanvalero.toastsapi.service;

import java.util.List;

import com.sanvalero.toastsapi.exception.NotFoundException;
import com.sanvalero.toastsapi.model.ProductType;

public interface ProductTypeService {
    ProductType findByName(String name);

    List<ProductType> findByType(String type);

    ProductType findByNameAndType(String name, String type);

    List<ProductType> findAll();

    ProductType findById(int id) throws NotFoundException;

    ProductType addType(ProductType productType);

    ProductType deleteType(ProductType productType);

    ProductType modifyType(ProductType productType);
}
