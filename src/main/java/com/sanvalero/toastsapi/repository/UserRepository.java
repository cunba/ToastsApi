package com.sanvalero.toastsapi.repository;

import com.sanvalero.toastsapi.model.User;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    
}
