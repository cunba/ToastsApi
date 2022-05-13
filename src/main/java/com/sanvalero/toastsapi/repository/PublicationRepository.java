package com.sanvalero.toastsapi.repository;

import java.time.LocalDate;

import com.sanvalero.toastsapi.model.Establishment;
import com.sanvalero.toastsapi.model.Publication;
import com.sanvalero.toastsapi.model.UserModel;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;

@Repository
public interface PublicationRepository extends ReactiveMongoRepository<Publication, String> {
    Flux<Publication> findAll();

    Flux<Publication> findByDate(LocalDate date);

    Flux<Publication> findByDateBetween(LocalDate minDate, LocalDate maxDate);

    Flux<Publication> findByTotalPrice(float price);

    Flux<Publication> findByTotalPriceBetween(float minPrice, float maxPrice);

    Flux<Publication> findByTotalPunctuation(float punctuation);

    Flux<Publication> findByTotalPunctuationBetween(float minPunctuation, float maxPunctuation);

    Flux<Publication> findByEstablishment(Establishment establishment);

    Flux<Publication> findByUser(UserModel user);

    Flux<Publication> findByDateBetweenAndTotalPriceBetweenAndTotalPunctuationBetween(LocalDate minDate,
            LocalDate maxDate, float minPrice, float maxPrice, float minPunctuation, float maxPunctuation);

    // @Query(value = "SELECT * FROM publications WHERE id IN (SELECT publication_id FROM products WHERE type_id IN (SELECT id FROM products_types WHERE product_name = :type))", nativeQuery = true)
    // Flux<Publication> findByProductType(String type);

    // @Query(value = "SELECT SUM(suma) FROM (SELECT SUM(price) AS suma FROM products WHERE publication_id = :id UNION ALL SELECT SUM(price) AS suma FROM menus WHERE id IN ( SELECT menu_id FROM products WHERE publication_id = :id)) T", nativeQuery = true)
    // float totalPrice(String id);

    // @Query(value = "SELECT SUM(suma)/SUM(num) FROM (SELECT SUM(punctuation) AS suma, COUNT(id) AS num FROM products WHERE publication_id = 1 UNION ALL SELECT SUM(punctuation) AS suma, COUNT(id) AS num FROM menus WHERE id IN ( SELECT menu_id FROM products WHERE publication_id = 1)) T", nativeQuery = true)
    // float totalPunctuation(String id);
}
