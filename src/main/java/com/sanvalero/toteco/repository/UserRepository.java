package com.sanvalero.toteco.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.sanvalero.toteco.model.UserModel;

public interface UserRepository extends CrudRepository<UserModel, UUID> {
    @Query(value = "SELECT COUNT(*) FROM publications WHERE user_id = :id", nativeQuery = true)
    int countPublications(UUID id);

    @Query(value = "SELECT SUM(total_price) FROM publications WHERE user_id = :id", nativeQuery = true)
    float sumMoney(UUID id);

    @Query(value = "SELECT recovery_code FROM users WHERE id = :id", nativeQuery = true)
    int findRecoveryCode(UUID id);

    List<UserModel> findByUsername(String username);

    List<UserModel> findByEmail(String email);
}
