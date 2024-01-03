package com.sanvalero.toteco.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sanvalero.toteco.model.Menu;

@Repository
public interface MenuRepository extends CrudRepository<Menu, UUID> {
    List<Menu> findAll();
}
