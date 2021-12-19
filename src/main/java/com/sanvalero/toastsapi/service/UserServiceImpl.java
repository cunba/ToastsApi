package com.sanvalero.toastsapi.service;

import java.util.List;

import com.sanvalero.toastsapi.exception.NotFoundException;
import com.sanvalero.toastsapi.model.User;
import com.sanvalero.toastsapi.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository ur;

    @Override
    public List<User> findAllUsers() {
        return (List<User>) ur.findAll();
    }

    @Override
    public User findById(int id) throws NotFoundException {
        return ur.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public int countPublications(int id) {
        return ur.countPublications(id);
    }

    @Override
    public float sumPrice(int id) {
        return ur.sumPrice(id);
    }

    @Override
    public User addUser(User user) {
        return ur.save(user);
    }

    @Override
    public User updateUser(User user) {
        return ur.save(user);
    }

    @Override
    public void updatePublicationsNumber(User user) {
        ur.save(user);
    }

    @Override
    public User deleteUser(User user) {
        ur.delete(user);
        return user;
    }

    @Override
    public void deleteAll() {
        ur.deleteAll();
    }
}
