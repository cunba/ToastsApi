package com.sanvalero.toastsapi.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "publications")
public class Publication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate date;
    @Column(name = "total_price")
    private float totalPrice;
    @Column(name = "total_punctuation")
    private float totalPunctuation;
    @Column
    private String photo;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference(value = "publication_user")
    private User user;
    @ManyToOne
    @JoinColumn(name = "establishment_id")
    @JsonBackReference(value = "publication_establishment")
    private Establishment establishment;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "publication")
    private List<Product> products;
}
