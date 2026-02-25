package org.example.hogwarts.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
public class Profesor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProfesor;

    private String nombre;
    private String apellido;
    private LocalDate fechaInicio;

    private boolean deleted = false;

    // Relación 1:1 Dueña (Tiene la columna id_asignatura)
    @OneToOne
    @JoinColumn(name = "id_asignatura")
    private Asignatura asignatura;

    // Relación 1:1 Inversa (Jefe de Casa)
    @OneToOne(mappedBy = "jefeCasa")
    @JsonIgnore
    private Casa casaDirigida;
}