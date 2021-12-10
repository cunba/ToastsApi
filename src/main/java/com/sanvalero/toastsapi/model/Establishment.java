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
@Entity(name = "establishments")
public class Establishment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column
    private String name;
    @Column(name = "creation_date")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate creationDate;
    @Column
    private String location;
    @Column
    private boolean open;
    @Column
    private float punctuation;

    @OneToMany(mappedBy = "establishment")
    private List<Publication> publications;
}
