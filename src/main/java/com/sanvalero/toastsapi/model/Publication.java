package com.sanvalero.toastsapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Publication")
public class Publication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private Date date;
    @Column(name = "total_price")
    private float totalPrice;
    @Column(name = "total_punctuation")
    private float totalPunctuation;
    @Column
    private String photo;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference(value = "publication-user")
    private User user;
    @ManyToOne
    @JoinColumn(name = "establishment_id")
    @JsonBackReference(value = "publication-establishment")
    private Establishment establishment;

    @OneToMany
    private List<Toast> toasts;
    @OneToMany
    private List<Coffee> coffees;
    @OneToMany
    private List<Tea> teas;
}
