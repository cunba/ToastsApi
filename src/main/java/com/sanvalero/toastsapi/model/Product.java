package com.sanvalero.toastsapi.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate date;
    @Column(name = "in_menu")
    @NotNull
    private boolean inMenu;
    @Column
    @PositiveOrZero
    private float price;
    @Column
    @NotNull
    @Min(value = 0)
    @Max(value = 5)
    private float score;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private ProductType type;
    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @ManyToOne
    @JoinColumn(name = "publication_id")
    private Publication publication;
}
