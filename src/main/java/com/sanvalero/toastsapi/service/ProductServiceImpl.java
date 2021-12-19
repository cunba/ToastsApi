package com.sanvalero.toastsapi.service;

import java.time.LocalDate;
import java.util.List;

import com.sanvalero.toastsapi.exception.NotFoundException;
import com.sanvalero.toastsapi.model.Menu;
import com.sanvalero.toastsapi.model.Product;
import com.sanvalero.toastsapi.model.ProductType;
import com.sanvalero.toastsapi.model.Publication;
import com.sanvalero.toastsapi.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository pr;

    @Override
    public List<Product> findByType(ProductType productType) {
        return pr.findByType(productType);
    }

    @Override
    public List<Product> findByDate(LocalDate date) {
        return pr.findByDate(date);
    }

    @Override
    public List<Product> findByDateBetween(LocalDate minDate, LocalDate maxDate) {
        return pr.findByDateBetween(minDate, maxDate);
    }

    @Override
    public List<Product> findByPrice(float price) {
        return pr.findByPrice(price);
    }

    @Override
    public List<Product> findByPriceBetween(float minPrice, float maxPrice) {
        return pr.findByPriceBetween(minPrice, maxPrice);
    }

    @Override
    public List<Product> findByPunctuation(float punctuation) {
        return pr.findByPunctuation(punctuation);
    }

    @Override
    public List<Product> findByPunctuationBetween(float minPunctuation, float maxPunctuation) {
        return pr.findByPunctuationBetween(minPunctuation, maxPunctuation);
    }

    @Override
    public List<Product> findByInMenu(boolean inMenu) {
        return pr.findByInMenu(inMenu);
    }

    @Override
    public List<Product> findByMenu(Menu menu) {
        return pr.findByMenu(menu);
    }

    @Override
    public List<Product> findByPublication(Publication publication) {
        return pr.findByPublication(publication);
    }

    @Override
    public Product findById(int id) throws NotFoundException {
        return pr.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<Product> findAll() {
        return pr.findAll();
    }

    @Override
    public Product addProduct(Product product) {
        return pr.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        return pr.save(product);
    }

    @Override
    public Product deleteProduct(Product product) {
        pr.delete(product);
        return product;
    }

    @Override
    public void deleteAll() {
        pr.deleteAll();
    }
}
