package com.sanvalero.toteco.model.dto;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstablishmentDTO {
    @NotNull
    private String name;
    @NotNull
    private String location;
    @AssertFalse
    @AssertTrue
    private boolean open;
}
