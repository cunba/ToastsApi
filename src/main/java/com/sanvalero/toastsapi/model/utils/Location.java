package com.sanvalero.toastsapi.model.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    private float latitude;
    private float longitude;

    @Override
    public String toString() {
        return this.latitude + ", " + this.longitude;
    }
}
