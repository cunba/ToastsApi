package com.sanvalero.toastsapi.repository;

import java.util.List;

import com.sanvalero.toastsapi.model.UserModel;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserModel, Integer> {
    @Query(value = "SELECT COUNT(*) FROM publications WHERE user_id = :id", nativeQuery = true)
    int countPublications(int id);

    @Query(value = "SELECT SUM(total_price) FROM publications WHERE user_id = :id", nativeQuery = true)
    float sumPrice(int id);

    List<UserModel> findByUsername(String username);

    List<UserModel> findByEmail(String email);
}
