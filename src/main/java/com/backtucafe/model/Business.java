package com.backtucafe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "business")
public class Business {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_business;

    @NotNull
    private String name;
    private String description;

    @NotNull
    @Email
    private String email;

    private String address;
    private String phone;
    private List<Byte> image;

    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    @Pattern(regexp = ".*[A-Z].*", message = "La contraseña debe contener al menos una letra mayúscula")
    @Pattern(regexp = ".*[^a-zA-Z0-9].*", message = "La contraseña debe contener al menos un carácter especial")
    @NotNull
    private String password;

    private String city;
    private LocalTime start_hour;
    private LocalTime finish_hour;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_admin", referencedColumnName = "id_admin")
    private Admin idAdmin;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "reservation",
            joinColumns = @JoinColumn(name = "id_business"),
            inverseJoinColumns = @JoinColumn(name = "id_client"))
    private Set<Client> clients = new HashSet<>();



}
