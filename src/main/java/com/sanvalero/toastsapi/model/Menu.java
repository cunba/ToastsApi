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
@Entity(name = "Menus")
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

    @OneToMany(mappedBy = "toast")
    private List<Toast> toasts;
    @OneToMany(mappedBy = "coffee")
    private List<Coffee> coffees;
    @OneToMany(mappedBy = "tea")
    private List<Tea> teas;
}
