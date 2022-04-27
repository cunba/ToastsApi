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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

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
    @NotNull
    @PositiveOrZero
    private float totalPrice;
    @Column(name = "total_punctuation")
    @Min(value = 0)
    @Max(value = 5)
    @NotNull
    private float totalPunctuation;
    @Column
    private String photo;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private UserModel user;
    @ManyToOne
    @JoinColumn(name = "establishment_id")
    @NotNull
    private Establishment establishment;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "publication")
    @JsonBackReference(value = "publication-products")
    private List<Product> products;
}
