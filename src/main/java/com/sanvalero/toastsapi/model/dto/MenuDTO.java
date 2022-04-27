package com.sanvalero.toastsapi.model.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuDTO {
    @NotNull
    @Positive
    private float price;
    @NotNull
    @Min(value = 0)
    @Max(value = 5)
    private float punctuation;
}
