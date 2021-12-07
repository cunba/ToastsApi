package com.sanvalero.toastsapi.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "Menu")
public class Menu {
    @Id
    private int id;
    @Column(name = "date")
    private Date date;
    @Column(name = "price")
    private float price;
    @Column(name = "punctuation")
    private float punctuation;
    @Column(name = "toast_id")
    private int toastId;
    @Column(name = "coffee_id")
    private int coffeeId;
}
