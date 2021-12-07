package com.sanvalero.toastsapi.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "Stablishment")
public class Stablishment {
    @Id
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "creation_date")
    private Date creationDate;
    @Column(name = "location")
    private String location;
    @Column(name = "open")
    private boolean open;
    @Column(name = "punctuation")
    private float punctuation;
}
