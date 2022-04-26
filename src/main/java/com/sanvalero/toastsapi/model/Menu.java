package com.sanvalero.toastsapi.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "menus")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate date;
    @Column
    private float price;
    @Column
    private float punctuation;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "menu")
    @JsonBackReference(value = "menu-products")
    private List<Product> products;
}
