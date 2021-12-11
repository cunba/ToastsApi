package com.sanvalero.toastsapi.service;

import java.time.LocalDate;
import java.util.List;

import com.sanvalero.toastsapi.exception.NotFoundException;
import com.sanvalero.toastsapi.model.Menu;
import com.sanvalero.toastsapi.model.Product;
import com.sanvalero.toastsapi.model.ProductType;
import com.sanvalero.toastsapi.model.Publication;
import com.sanvalero.toastsapi.model.dto.ProductDTO;

public interface ProductService {
    List<Product> findByDate(LocalDate date);

    List<Product> findByDateBetween(LocalDate minDate, LocalDate maxDate);

    List<Product> findByPrice(float price);

    List<Product> findByPriceBetween(float minPrice, float maxPrice);

    List<Product> findByPunctuation(float punctuation);

    List<Product> findByPunctuationBetween(float minPunctuation, float maxPunctuation);

    List<Product> findByWithMenu(boolean withMenu);

    List<Product> findByMenu(Menu menu);

    List<Product> findByPublication(Publication publication);

    List<Product> findAll();

    Product findById(int id) throws NotFoundException;

    List<Product> findByType(ProductType productType);

    List<Product> findByTypes(List<ProductType> productTypeList);

    Product addProduct(ProductDTO productDTO) throws NotFoundException;

    Product deleteProduct(int id) throws NotFoundException;

    Product modifyProduct(Product product);
}
