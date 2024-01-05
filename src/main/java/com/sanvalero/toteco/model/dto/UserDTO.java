package com.sanvalero.toteco.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @NotNull
    @NotBlank
    @NotEmpty
    private String username;
    @NotNull
    @NotBlank
    @NotEmpty
    private String name;
    @NotNull
    @NotBlank
    @NotEmpty
    private String surname;
    @Past
    private long birthDate;
    @NotNull
    @NotBlank
    @NotEmpty
    @Email
    private String email;
    @NotNull
    @NotBlank
    @NotEmpty
    private String image;
    @NotNull
    @NotBlank
    @NotEmpty
    private String password;
    @NotNull
    @NotBlank
    @NotEmpty
    private String role;
}
