package com.sanvalero.toastsapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "publications")
public class Publication {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column
    private LocalDate date;
    @Column(name = "total_price")
    private float totalPrice;
    @Column(name = "total_punctuation")
    private float totalPunctuation;
    @Column
    private String photo;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;
    @ManyToOne
    @JoinColumn(name = "establishment_id")
    @JsonBackReference
    private Establishment establishment;

    @OneToMany(mappedBy = "publication")
    private List<Toast> toasts;
    @OneToMany(mappedBy = "publication")
    private List<Coffee> coffees;
    @OneToMany(mappedBy = "publication")
    private List<Tea> teas;
}
