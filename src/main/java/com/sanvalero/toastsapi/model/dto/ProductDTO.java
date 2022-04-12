package com.sanvalero.toastsapi.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDTO {

    private boolean inMenu;
    private float price;
    private float punctuation;
    private int typeId;
    private int menuId;
    private int publicationId;
}
