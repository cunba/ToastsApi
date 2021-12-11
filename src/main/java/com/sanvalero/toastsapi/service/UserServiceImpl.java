package com.sanvalero.toastsapi.service;

import java.util.List;

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
    public User findById(int id) {
        return findById(id);
    }

    @Override
    public User addUser(User user) {
        return ur.save(user);
    }

    @Override
    public User deleteUser(int id) {
        return null;
    }

    @Override
    public User modifyUser(User user, int id) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
