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

    List<Publication> findByTotalScore(float score);

    List<Publication> findByTotalScoreBetween(float minScore, float maxScore);

    List<Publication> findByEstablishment(Establishment establishment);

    List<Publication> findByUser(UserModel user);

    List<Publication> findByDateBetweenAndTotalPriceBetweenAndTotalScoreBetween(LocalDate minDate, LocalDate maxDate, float minPrice, float maxPrice, float minScore, float maxScore);

    @Query(value = "SELECT * FROM publications WHERE id IN (SELECT publication_id FROM products WHERE type_id IN (SELECT id FROM products_types WHERE product_name = :type))", nativeQuery = true)
    List<Publication> findByProductType(String type);

    @Query(value = "SELECT SUM(suma) FROM (SELECT SUM(price) AS suma FROM products WHERE publication_id = :id UNION ALL SELECT SUM(price) AS suma FROM menus WHERE id IN ( SELECT menu_id FROM products WHERE publication_id = :id)) T", nativeQuery = true)
    float totalPrice(int id);

    @Query(value = "SELECT SUM(suma)/SUM(num) FROM (SELECT SUM(score) AS suma, COUNT(id) AS num FROM products WHERE publication_id = 1 UNION ALL SELECT SUM(score) AS suma, COUNT(id) AS num FROM menus WHERE id IN ( SELECT menu_id FROM products WHERE publication_id = 1)) T", nativeQuery = true)
    float totalScore(int id);    
}
