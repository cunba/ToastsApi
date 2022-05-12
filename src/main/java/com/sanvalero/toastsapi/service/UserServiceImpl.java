package com.sanvalero.toastsapi.service;

import com.sanvalero.toastsapi.exception.NotFoundException;
import com.sanvalero.toastsapi.model.UserModel;
import com.sanvalero.toastsapi.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository ur;

    @Override
    public Flux<UserModel> findAllUsers() {
        return ur.findAll();
    }

    @Override
    public Mono<UserModel> findById(String id) throws NotFoundException {
        return ur.findById(id).onErrorReturn(new UserModel());
    }

    @Override
    public Flux<UserModel> findByUsername(String username) {
        return ur.findByUsername(username);
    }

    @Override
    public Flux<UserModel> findByEmail(String email) {
        return ur.findByEmail(email);
    }

    @Override
    public int countPublications(String id) {
        return ur.countPublications(id);
    }

    @Override
    public float sumPrice(String id) throws NullPointerException {
        return ur.sumPrice(id);
    }

    @Override
    public Mono<UserModel> addUser(UserModel user) {
        return ur.save(user);
    }

    @Override
    public void updatePublicationsNumber(UserModel user) {
        ur.save(user);
    }

    @Override
    public void updateMoneySpent(UserModel user) {
        ur.save(user);
    }

    @Override
    public void updatePassword(UserModel user) {
        ur.save(user);
    }

    @Override
    public void disable(UserModel user) {
        ur.save(user);
    }

    @Override
    public void activate(UserModel user) {
        ur.save(user);
    }

    @Override
    public void deleteUser(UserModel user) {
        ur.delete(user);
    }

    @Override
    public void deleteAll() {
        ur.deleteAll();
    }
}
