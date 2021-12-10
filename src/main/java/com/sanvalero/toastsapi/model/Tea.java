package com.sanvalero.toastsapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "teas")
public class Tea {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column
    private LocalDate date;
    @Column(name = "menu")
    private boolean withMenu;
    @Column
    private float price;
    @Column
    private float punctuation;

    @ManyToOne
    @JoinColumn(name = "type_id")
    @JsonBackReference
    private TeaType type;
    @ManyToOne
    @JoinColumn(name = "menu_id")
    @JsonBackReference
    private Menu menu;
    @ManyToOne
    @JoinColumn(name = "publication_id")
    @JsonBackReference
    private Publication publication;
}
