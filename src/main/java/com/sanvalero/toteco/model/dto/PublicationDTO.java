package com.sanvalero.toteco.model.dto;

import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PublicationDTO {

    private String image;
    @NotNull
    @NotBlank
    @NotEmpty
    private UUID userId;
    @NotNull
    @NotBlank
    @NotEmpty
    private UUID establishmentId;
    @NotNull
    private float totalScore;
    @NotNull
    private float totalPrice;
}
