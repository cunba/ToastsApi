package com.sanvalero.toteco.model;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class UserModel {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36, nullable = false, updatable = false)
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;
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
    @NotNull
    private long birthDate;
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
    private String image;
    @Column
    @NotNull
    @NotEmpty
    @NotBlank
    private String password;
    @Column
    private long created;
    @Column
    private long modified;
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
    @Column(name = "recovery_code")
    private int recoveryCode;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @JsonBackReference(value = "user-publications")
    private List<Publication> publications;

    @Bean
    public static PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
