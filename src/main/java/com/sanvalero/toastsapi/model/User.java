package com.sanvalero.toastsapi.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String username;
    @Column
    private String name;
    @Column
    private String surname;
    @Column(name = "birth_date")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate birthDate;
    @Column
    private String email;
    @Column
    private String password;
    @Column(name = "creation_date")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate creationDate;
    @Column
    private boolean active;
    @Column(name = "money_spent")
    private float moneySpent;
    @Column(name = "publications_number")
    private int publicationsNumber;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Publication> publications;
}
