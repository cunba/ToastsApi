package com.sanvalero.toastsapi.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "menus")
public class Menu {
    @Id
    private String id;
    @Field
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate date;
    @Field
    @NotNull
    @PositiveOrZero
    private float price;
    @Field
    @NotNull
    @Min(value = 0)
    @Max(value = 5)
    private float punctuation;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "menu")
    @JsonBackReference(value = "menu-products")
    private List<Product> products;
}
