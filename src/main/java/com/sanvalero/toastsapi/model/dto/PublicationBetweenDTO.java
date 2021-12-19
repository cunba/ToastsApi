package com.sanvalero.toastsapi.model.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PublicationBetweenDTO {
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate minDate;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate maxDate;
    private float minPrice;
    private float maxPrice;
    private float minPunctuation;
    private float maxPunctuation;
}
