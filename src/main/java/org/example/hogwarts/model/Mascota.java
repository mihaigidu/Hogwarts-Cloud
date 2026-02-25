package org.example.hogwarts.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Mascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMascota;

    @Column(name = "nombre_mascota")
    private String nombre;

    private String especie;

    // Relación 1:1 Dueña (Tiene la columna id_estudiante)
    @OneToOne
    @JoinColumn(name = "id_estudiante")
    @JsonIgnore
    private Estudiante estudiante;
}