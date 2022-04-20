package com.sanvalero.toastsapi.model.utils;

import java.io.Serializable;

import lombok.Data;

@Data
public class Location implements Serializable {
    private float latitude;
    private float longitude;
}
