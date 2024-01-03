package com.sanvalero.toteco.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanvalero.toteco.exception.NotFoundException;
import com.sanvalero.toteco.model.UserModel;
import com.sanvalero.toteco.repository.UserRepository;
import com.sanvalero.toteco.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository ur;

    @Override
    public List<UserModel> findAll() {
        return (List<UserModel>) ur.findAll();
    }

    @Override
    public UserModel findById(UUID id) throws NotFoundException {
        return ur.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<UserModel> findByUsername(String username) {
        return ur.findByUsername(username);
    }

    @Override
    public List<UserModel> findByEmail(String email) {
        return ur.findByEmail(email);
    }

    @Override
    public int findRecoveryCode(UUID id) {
        return ur.findRecoveryCode(id);
    }

    @Override
    public int countPublications(UUID id) {
        return ur.countPublications(id);
    }

    @Override
    public float sumMoney(UUID id) throws NullPointerException {
        return ur.sumMoney(id);
    }

    @Override
    public UserModel save(UserModel user) {
        return ur.save(user);
    }

    @Override
    public void update(UserModel user) {
        ur.save(user);
    }

    @Override
    public void delete(UserModel user) {
        ur.delete(user);
    }

    @Override
    public void deleteAll() {
        ur.deleteAll();
    }

}
