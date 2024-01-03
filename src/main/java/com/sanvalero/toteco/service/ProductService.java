package com.sanvalero.toteco.service;

import java.util.List;

import com.sanvalero.toteco.model.Menu;
import com.sanvalero.toteco.model.Product;
import com.sanvalero.toteco.model.Publication;

public interface ProductService extends GlobalService<Product> {

    List<Product> findByMenu(Menu menu);

    List<Product> findByPublication(Publication publication);

}
