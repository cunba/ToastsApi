package com.sanvalero.toastsapi.service;

import java.util.List;

import com.sanvalero.toastsapi.exception.NotFoundException;
import com.sanvalero.toastsapi.model.User;

public interface UserService {
    List<User> findAllUsers();

    User findById(int id) throws NotFoundException;

    int countPublications(int id);

    float sumPrice(int id);

    User addUser(User user);

    void updatePublicationsNumber(User user);

    void updateMoneySpent(User user);

    void updatePassword(User user);

    void disable(User user);

    void activate(User user);

    User deleteUser(User user);

    void deleteAll();
}
