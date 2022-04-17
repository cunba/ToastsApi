package com.sanvalero.toastsapi.model.dto;

import com.sanvalero.toastsapi.model.utils.Location;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstablishmentDTO {
    private String name;
    private Location location;
    private boolean open;
}
