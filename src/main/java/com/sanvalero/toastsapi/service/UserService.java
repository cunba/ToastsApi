package com.sanvalero.toastsapi.service;

import java.util.List;

import com.sanvalero.toastsapi.exception.NotFoundException;
import com.sanvalero.toastsapi.model.UserModel;

public interface UserService {
    List<UserModel> findAllUsers();

    UserModel findById(int id) throws NotFoundException;

    List<UserModel> findByUsername(String username);

    int countPublications(int id);

    float sumPrice(int id);

    UserModel addUser(UserModel user);

    void updatePublicationsNumber(UserModel user);

    void updateMoneySpent(UserModel user);

    void updatePassword(UserModel user);

    void disable(UserModel user);

    void activate(UserModel user);

    UserModel deleteUser(UserModel user);

    void deleteAll();
}
