package com.sanvalero.toastsapi.model;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.PositiveOrZero;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "users")
public class UserModel {

    @Id
    private UUID _id;
    @Field
    @NotNull
    @NotEmpty
    @NotBlank
    private String username;
    @Field
    @NotNull
    private String name;
    @Field
    @NotNull
    private String surname;
    @Field(name = "birth_date")
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Past
    private LocalDate birthDate;
    @Field
    @Email
    @NotNull
    @NotEmpty
    @NotBlank
    private String email;
    @Field
    @NotNull
    @NotEmpty
    @NotBlank
    private String password;
    @Field(name = "creation_date")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate creationDate;
    @Field
    @NotNull
    private boolean active;
    @Field(name = "money_spent")
    @PositiveOrZero
    private float moneySpent;
    @Field(name = "publications_number")
    @PositiveOrZero
    private int publicationsNumber;
    @Field
    @NotNull
    @NotEmpty
    @NotBlank
    private String role;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @JsonBackReference(value = "user-publications")
    private List<Publication> publications;

    @Bean
    public static PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
