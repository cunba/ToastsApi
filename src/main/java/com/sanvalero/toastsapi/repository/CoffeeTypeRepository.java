package com.sanvalero.toastsapi.repository;

import com.sanvalero.toastsapi.model.CoffeeType;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoffeeTypeRepository extends CrudRepository<CoffeeType, Integer> {
    
}
