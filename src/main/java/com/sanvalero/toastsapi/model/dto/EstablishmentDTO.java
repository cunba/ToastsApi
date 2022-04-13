package com.sanvalero.toastsapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstablishmentDTO {
    private String name;
    private String location;
    private boolean open;
}
