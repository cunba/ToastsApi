package com.sanvalero.toastsapi.repository;

import com.sanvalero.toastsapi.model.UserModel;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Flux;

public interface UserRepository extends ReactiveCrudRepository<UserModel, Integer> {
    @Query(value = "SELECT COUNT(*) FROM publications WHERE user_id = :id", nativeQuery = true)
    int countPublications(int id);

    @Query(value = "SELECT SUM(total_price) FROM publications WHERE user_id = :id", nativeQuery = true)
    float sumPrice(int id);

    Flux<UserModel> findByUsername(String username);

    Flux<UserModel> findByEmail(String email);
}
