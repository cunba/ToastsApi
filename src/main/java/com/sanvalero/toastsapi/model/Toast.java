package com.sanvalero.toastsapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Toasts")
public class Toast {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String type;
    @Column
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate date;
    @Column(name = "menu")
    private boolean withMenu;
    @Column
    private float price;
    @Column
    private float punctuation;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    @JsonBackReference(value = "toast-menu")
    private Menu menu;
    @ManyToOne
    @JoinColumn(name = "publication_id")
    @JsonBackReference(value = "toast-publication")
    private Publication publication;
}
