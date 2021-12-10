package com.sanvalero.toastsapi.service;

import java.util.List;

import com.sanvalero.toastsapi.model.User;

public interface UserService {
    List<User> findAllUsers();

    User findById(int id);

    User addUser(User user);

    User deleteUser(int id);

    User modifyUser(User user, int id);
}
