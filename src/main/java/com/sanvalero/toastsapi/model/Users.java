package com.sanvalero.toastsapi.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "Users")
public class Users {

    @Id
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "email")
    private String email;
    @Column(name = "creation_date")
    private Date creationDate;
    @Column(name = "active")
    private boolean active;
    @Column(name = "money_spent")
    private float moneySpent;
    @Column(name = "publications_number")
    private int publicationsNumber;
}
