package com.sanvalero.toastsapi.repository;

import com.sanvalero.toastsapi.model.UserModel;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Flux;

public interface UserRepository extends ReactiveMongoRepository<UserModel, String> {
    // @Query(value = "SELECT COUNT(*) FROM publications WHERE user_id = :id", nativeQuery = true)
    // int countPublications(String id);

    // @Query(value = "SELECT SUM(total_price) FROM publications WHERE user_id = :id", nativeQuery = true)
    // float sumPrice(String id);

    Flux<UserModel> findByUsername(String username);

    Flux<UserModel> findByEmail(String email);
}
