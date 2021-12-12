package com.sanvalero.toastsapi.service;

import java.util.List;
import java.util.Vector;

import com.sanvalero.toastsapi.exception.NotFoundException;
import com.sanvalero.toastsapi.model.ProductType;

public interface ProductTypeService {
    List<ProductType> findByProductName(String name);

    ProductType findByType(String type);

    ProductType findByProductNameAndType(String name, String type);

    List<ProductType> findAll();

    ProductType findById(int id) throws NotFoundException;

    List<ProductType> findByIds(Vector<Integer> ids);

    ProductType addType(ProductType type);

    ProductType deleteType(ProductType type);

    ProductType modifyType(ProductType type);

    void deleteAll();
}
