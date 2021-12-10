package com.sanvalero.toastsapi.repository;

import com.sanvalero.toastsapi.model.TeaType;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeaTypeRepository extends CrudRepository<TeaType, Integer> {
    
}
