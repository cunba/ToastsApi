package com.sanvalero.toastsapi.service;

import com.sanvalero.toastsapi.exception.NotFoundException;
import com.sanvalero.toastsapi.model.UserModel;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Flux<UserModel> findAllUsers();

    Mono<UserModel> findById(int id) throws NotFoundException;

    Flux<UserModel> findByUsername(String username);

    Flux<UserModel> findByEmail(String email);

    int countPublications(int id);

    float sumPrice(int id) throws NullPointerException;

    Mono<UserModel> addUser(UserModel user);

    void updatePublicationsNumber(UserModel user);

    void updateMoneySpent(UserModel user);

    void updatePassword(UserModel user);

    void disable(UserModel user);

    void activate(UserModel user);

    void deleteUser(UserModel user);

    void deleteAll();
}
