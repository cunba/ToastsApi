package com.sanvalero.toastsapi.model;

import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "products")
public class Product {

    @Id
    private UUID _id;
    @Field
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate date;
    @Field(name = "in_menu")
    @NotNull
    private boolean inMenu;
    @Field
    @PositiveOrZero
    private float price;
    @Field
    @NotNull
    @Min(value = 0)
    @Max(value = 5)
    private float punctuation;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private ProductType type;
    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;
    @ManyToOne
    @JoinColumn(name = "publication_id")
    private Publication publication;
}
