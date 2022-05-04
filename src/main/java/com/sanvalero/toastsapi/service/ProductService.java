package com.sanvalero.toastsapi.service;

import java.time.LocalDate;
import java.util.List;

import com.sanvalero.toastsapi.exception.NotFoundException;
import com.sanvalero.toastsapi.model.Menu;
import com.sanvalero.toastsapi.model.Product;
import com.sanvalero.toastsapi.model.ProductType;
import com.sanvalero.toastsapi.model.Publication;

public interface ProductService {
    List<Product> findByDate(LocalDate date);

    List<Product> findByDateBetween(LocalDate minDate, LocalDate maxDate);

    List<Product> findByPrice(float price);

    List<Product> findByPriceBetween(float minPrice, float maxPrice);

    List<Product> findByPunctuation(float punctuation);

    List<Product> findByPunctuationBetween(float minPunctuation, float maxPunctuation);

    List<Product> findByInMenu(boolean inMenu);

    List<Product> findByMenu(Menu menu);

    List<Product> findByPublication(Publication publication);

    List<Product> findAll();

    Product findById(int id) throws NotFoundException;

    List<Product> findByType(ProductType productType);

    Product addProduct(Product product);

    Product updateProduct(Product product);

    void updatePrice(Product product);

    void updatePunctuation(Product product);

    void deleteProduct(Product product);

    void deleteAll();
}
