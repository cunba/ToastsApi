package com.sanvalero.toastsapi.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PublicationDTO {

    private String photo;
    @NotNull
    @NotBlank
    @NotEmpty
    private int userId;
    @NotNull
    @NotBlank
    @NotEmpty
    private int establishmentId;
    @NotNull
    private float totalPunctuation;
    @NotNull
    private float totalPrice;
}
