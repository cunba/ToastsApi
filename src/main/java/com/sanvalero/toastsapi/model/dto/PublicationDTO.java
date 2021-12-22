package com.sanvalero.toastsapi.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PublicationDTO {

    private int id;
    private String photo;
    private int userId;
    private int establishmentId;
}
