package com.sanvalero.toteco.model;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "publications")
public class Publication {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36, nullable = false, updatable = false)
    private UUID id;
    @Column
    private long created;
    @Column(name = "total_price")
    @NotNull
    @PositiveOrZero
    private float totalPrice;
    @Column(name = "total_score")
    @Min(value = 0)
    @Max(value = 5)
    @NotNull
    private float totalScore;
    @Column
    private byte[] photo;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private UserModel user;
    @ManyToOne
    @JoinColumn(name = "establishment_id")
    @NotNull
    private Establishment establishment;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "publication")
    @JsonBackReference(value = "publication-products")
    private List<Product> products;
}
