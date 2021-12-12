package com.sanvalero.toastsapi.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class PublicationDTO {

    private int id;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate date;
    private float totalPrice;
    private float totalPunctuation;
    private String photo;
    private int userId;
    private int establishmentId;
}
