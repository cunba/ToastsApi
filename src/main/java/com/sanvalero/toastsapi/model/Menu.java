package com.sanvalero.toastsapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "menus")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate date;
    @Column
    private float price;
    @Column
    private float punctuation;

    @OneToMany(mappedBy = "menu")
    private List<Toast> toasts;
    @OneToMany(mappedBy = "menu")
    private List<Coffee> coffees;
    @OneToMany(mappedBy = "menu")
    private List<Tea> teas;
}
