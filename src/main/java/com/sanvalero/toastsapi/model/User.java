package com.sanvalero.toastsapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column
    private String name;
    @Column
    private String surname;
    @Column(name = "birth_date")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthDate;
    @Column
    private String email;
    @Column
    private String password;
    @Column(name = "creation_date")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate creationDate;
    @Column
    private boolean active;
    @Column(name = "money_spent")
    private float moneySpent;
    @Column(name = "publications_number")
    private int publicationsNumber;

    @OneToMany(mappedBy = "user")
    private List<Publication> publications;
}
