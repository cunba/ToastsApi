package com.sanvalero.toastsapi.service;

import java.util.List;

import com.sanvalero.toastsapi.exception.NotFoundException;
import com.sanvalero.toastsapi.model.ProductType;

public interface ProductTypeService {
    List<ProductType> findByProductName(String name);

    ProductType findByType(String type);

    ProductType findByProductNameAndType(String name, String type);

    List<ProductType> findAll();

    ProductType findById(int id) throws NotFoundException;

    ProductType addType(ProductType productType);

    ProductType deleteType(ProductType productType);

    ProductType modifyType(ProductType productType);
}
