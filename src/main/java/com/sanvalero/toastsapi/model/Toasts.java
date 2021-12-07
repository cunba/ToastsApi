package com.sanvalero.toastsapi.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "Toasts")
public class Toasts {

    @Id
    private int id;
    @Column(name = "type")
    private String type;
    @Column(name = "date")
    private Date date;
    @Column(name = "menu")
    private boolean menu;
    @Column(name = "price")
    private float price;
    @Column(name = "punctuation")
    private float punctuation;
}
