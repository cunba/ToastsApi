package com.sanvalero.toastsapi.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    @NotNull
    @NotEmpty
    @NotBlank
    private String username;
    @Column
    @NotNull
    private String name;
    @Column
    @NotNull
    private String surname;
    @Column(name = "birth_date")
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Past
    private LocalDate birthDate;
    @Column
    @Email
    @NotNull
    @NotEmpty
    @NotBlank
    private String email;
    @Column
    @NotNull
    @NotEmpty
    @NotBlank
    private String password;
    @Column(name = "creation_date")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate creationDate;
    @Column
    @NotNull
    private boolean active;
    @Column(name = "money_spent")
    @PositiveOrZero
    private float moneySpent;
    @Column(name = "publications_number")
    @PositiveOrZero
    private int publicationsNumber;
    @Column
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
