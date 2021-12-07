package com.sanvalero.toastsapi.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "Publication")
public class Publication {
    @Id
    private int id;
    @Column(name = "date")
    private Date date;
    @Column(name = "total_price")
    private float totalPrice;
    @Column(name = "total_punctuation")
    private float totalPunctuation;
    @Column(name = "photo")
    private String photo;
    @Column(name = "toast_id")
    private int toast_id;
    @Column(name = "coffee_id")
    private int coffee_id;
    @Column(name = "tea_id")
    private int tea_id;
    @Column(name = "menu_id")
    private int menu_id;
    @Column(name = "user_id")
    private int user_id;
    @Column(name = "stablishment_id")
    private int stablishment_id;
}
