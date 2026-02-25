package org.example.hogwarts.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
public class Asignatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAsignatura;

    @Column(name = "nombre_asignatura")
    private String nombre;

    private String aula;
    private boolean obligatoria;

    // Relación 1:1 con Profesor (La inversa)
    @OneToOne(mappedBy = "asignatura")
    @JsonIgnore
    private Profesor profesor;

    // Relación N:M con Estudiante (La inversa)
    @ManyToMany(mappedBy = "asignaturas")
    @JsonIgnore
    private List<Estudiante> estudiantes;
}