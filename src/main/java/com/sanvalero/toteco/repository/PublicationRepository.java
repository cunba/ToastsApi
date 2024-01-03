package com.sanvalero.toteco.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sanvalero.toteco.model.Establishment;
import com.sanvalero.toteco.model.Publication;
import com.sanvalero.toteco.model.UserModel;

@Repository
public interface PublicationRepository extends CrudRepository<Publication, UUID> {
    List<Publication> findAll();

    Optional<Publication> findById(UUID id);

    List<Publication> findByEstablishment(Establishment establishment);

    List<Publication> findByUser(UserModel user);
}
