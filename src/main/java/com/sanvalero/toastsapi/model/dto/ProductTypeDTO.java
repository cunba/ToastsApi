package com.sanvalero.toastsapi.model.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductTypeDTO {
    @NotNull
    private String type;
    @NotNull
    private String productName;
}
