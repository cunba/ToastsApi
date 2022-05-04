package com.sanvalero.toastsapi.service;

import java.util.List;

import com.sanvalero.toastsapi.exception.NotFoundException;
import com.sanvalero.toastsapi.model.UserModel;
import com.sanvalero.toastsapi.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository ur;

    @Override
    public List<UserModel> findAllUsers() {
        return (List<UserModel>) ur.findAll();
    }

    @Override
    public UserModel findById(int id) throws NotFoundException {
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
    public int countPublications(int id) {
        return ur.countPublications(id);
    }

    @Override
    public float sumPrice(int id) throws NullPointerException {
        return ur.sumPrice(id);
    }

    @Override
    public UserModel addUser(UserModel user) {
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
    public UserModel deleteUser(UserModel user) {
        ur.delete(user);
        return user;
    }

    @Override
    public void deleteAll() {
        ur.deleteAll();
    }
}
