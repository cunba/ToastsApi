package com.sanvalero.toastsapi.model.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDTO {

    private int id;
    private String name;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate date;
    private boolean inMenu;
    private float price;
    private float punctuation;
    private int typeId;
    private int menuId;
    private int publicationId;
}
