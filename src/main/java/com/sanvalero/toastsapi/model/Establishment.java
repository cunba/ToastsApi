package com.sanvalero.toastsapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Establishments")
public class Establishment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @Column(name = "creation_date")
    private Date creationDate;
    @Column
    private String location;
    @Column
    private boolean open;
    @Column
    private float punctuation;

    @OneToMany(mappedBy = "publication")
    private List<Publication> publications;
}
