package com.sanvalero.toastsapi.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sanvalero.toastsapi.model.utils.Location;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "establishments")
// @TypeDef(name = "json", typeClass = JsonStringType.class)
public class Establishment implements Serializable {
    @Id
    private UUID _id;
    @Field
    @NotNull
    private String name;
    @Field(name = "creation_date")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate creationDate;
    @Field
    private Location location;
    @Field
    @NotNull
    private boolean open;
    @Field
    @Min(value = 0)
    @Max(value = 5)
    private float punctuation;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "establishment")
    @JsonBackReference(value = "establishment-publications")
    private List<Publication> publications;
}
