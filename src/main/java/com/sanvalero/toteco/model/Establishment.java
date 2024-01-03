package com.sanvalero.toteco.model;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "establishments")
public class Establishment implements Serializable {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36, nullable = false, updatable = false)
    private UUID id;
    @Column
    @NotNull
    private String name;
    @Column
    private long created;
    @Column
    private String location;
    @Column
    @NotNull
    private boolean open;
    @Column
    @Min(value = 0)
    @Max(value = 5)
    private float score;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "establishment")
    @JsonBackReference(value = "establishment-publications")
    private List<Publication> publications;
}
