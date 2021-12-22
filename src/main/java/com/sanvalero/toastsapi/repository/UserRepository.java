package com.sanvalero.toastsapi.repository;

import com.sanvalero.toastsapi.model.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    @Query(value = "SELECT COUNT(*) FROM publications WHERE user_id = :id", nativeQuery = true)
    int countPublications(int id);

    @Query(value = "SELECT SUM(total_price) FROM publications WHERE user_id = :id", nativeQuery = true)
    float sumPrice(int id);
}
