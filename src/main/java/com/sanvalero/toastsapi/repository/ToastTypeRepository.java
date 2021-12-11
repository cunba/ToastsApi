package com.sanvalero.toastsapi.repository;

import com.sanvalero.toastsapi.model.ToastType;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToastTypeRepository extends CrudRepository<ToastType, Integer> {
    
}
