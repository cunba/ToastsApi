package com.sanvalero.toastsapi.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sanvalero.toastsapi.model.utils.Location;
import com.vladmihalcea.hibernate.type.json.JsonStringType;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "establishments")
@TypeDef(name = "json", typeClass = JsonStringType.class)
public class Establishment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    @NotNull
    private String name;
    @Column(name = "creation_date")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate creationDate;
    @Type(type = "json")
    @Column(columnDefinition = "json")
    private Location location;
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
