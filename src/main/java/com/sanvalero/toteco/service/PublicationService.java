package com.sanvalero.toteco.service;

import java.util.List;

import com.sanvalero.toteco.model.Establishment;
import com.sanvalero.toteco.model.Publication;
import com.sanvalero.toteco.model.UserModel;

public interface PublicationService extends GlobalService<Publication> {

    List<Publication> findByEstablishment(Establishment establishment);

    List<Publication> findByUser(UserModel user);

}
