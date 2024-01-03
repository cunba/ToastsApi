package com.sanvalero.toteco.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanvalero.toteco.exception.NotFoundException;
import com.sanvalero.toteco.model.Menu;
import com.sanvalero.toteco.model.Product;
import com.sanvalero.toteco.model.Publication;
import com.sanvalero.toteco.repository.ProductRepository;
import com.sanvalero.toteco.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository pr;

    @Override
    public List<Product> findByMenu(Menu menu) {
        return pr.findByMenu(menu);
    }

    @Override
    public List<Product> findByPublication(Publication publication) {
        return pr.findByPublication(publication);
    }

    @Override
    public Product findById(UUID id) throws NotFoundException {
        return pr.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<Product> findAll() {
        return pr.findAll();
    }

    @Override
    public Product save(Product product) {
        return pr.save(product);
    }

    @Override
    public void update(Product product) {
        pr.save(product);
    }

    @Override
    public void delete(Product product) {
        pr.delete(product);
    }

    @Override
    public void deleteAll() {
        pr.deleteAll();
    }
}
