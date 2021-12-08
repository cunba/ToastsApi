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
@Entity(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String email;
    @Column(name = "creation_date")
    private Date creationDate;
    @Column
    private boolean active;
    @Column(name = "money_spent")
    private float moneySpent;
    @Column(name = "publications_number")
    private int publicationsNumber;

    @OneToMany(mappedBy = "user")
    private List<Publication> publications;
}
