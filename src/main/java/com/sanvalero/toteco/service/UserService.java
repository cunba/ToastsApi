package com.sanvalero.toteco.service;

import java.util.List;
import java.util.UUID;

import com.sanvalero.toteco.model.UserModel;

public interface UserService extends GlobalService<UserModel> {

    List<UserModel> findByUsername(String username);

    List<UserModel> findByEmail(String email);

    int countPublications(UUID id);

    float sumMoney(UUID id) throws NullPointerException;

    int findRecoveryCode(UUID id);

}
