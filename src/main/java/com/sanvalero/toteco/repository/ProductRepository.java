package com.sanvalero.toteco.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sanvalero.toteco.model.Menu;
import com.sanvalero.toteco.model.Product;
import com.sanvalero.toteco.model.Publication;

@Repository
public interface ProductRepository extends CrudRepository<Product, UUID> {
    List<Product> findAll();

    List<Product> findByName(String name);

    List<Product> findByInMenu(boolean inMenu);

    List<Product> findByMenu(Menu menu);

    List<Product> findByPublication(Publication publication);
}
