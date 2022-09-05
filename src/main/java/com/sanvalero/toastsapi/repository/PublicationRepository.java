package com.sanvalero.toastsapi.repository;

import java.time.LocalDate;
import java.util.List;

import com.sanvalero.toastsapi.model.Establishment;
import com.sanvalero.toastsapi.model.Publication;
import com.sanvalero.toastsapi.model.UserModel;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicationRepository extends CrudRepository<Publication, Integer> {
    List<Publication> findAll();

    List<Publication> findByDate(LocalDate date);

    List<Publication> findByDateBetween(LocalDate minDate, LocalDate maxDate);

    List<Publication> findByTotalPrice(float price);

    List<Publication> findByTotalPriceBetween(float minPrice, float maxPrice);

    List<Publication> findByTotalPunctuation(float punctuation);

    List<Publication> findByTotalPunctuationBetween(float minPunctuation, float maxPunctuation);

    List<Publication> findByEstablishment(Establishment establishment);

    List<Publication> findByUser(UserModel user);

    List<Publication> findByDateBetweenAndTotalPriceBetweenAndTotalPunctuationBetween(LocalDate minDate, LocalDate maxDate, float minPrice, float maxPrice, float minPunctuation, float maxPunctuation);

    @Query(value = "SELECT * FROM publications WHERE id IN (SELECT publication_id FROM products WHERE type_id IN (SELECT id FROM products_types WHERE product_name = :type))", nativeQuery = true)
    List<Publication> findByProductType(String type);   
}
