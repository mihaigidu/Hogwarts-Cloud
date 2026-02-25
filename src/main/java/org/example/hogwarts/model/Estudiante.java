package org.example.hogwarts.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SoftDelete;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@Entity
@SoftDelete
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEstudiante;

    private String nombre;
    private String apellido;
    private int anyoCurso;
    private LocalDate fechaNacimiento;

    // Relación N:1 con Casa
    @ManyToOne
    @JoinColumn(name = "id_casa")
    private Casa casa;

    // Relación 1:1 Inversa con Mascota
    @OneToOne(mappedBy = "estudiante", cascade = CascadeType.ALL, orphanRemoval = true)
    private Mascota mascota;

    // Relación N:M con Asignatura (Tabla intermedia)
    @ManyToMany
    @JoinTable(
            name = "estudiante_asignatura",
            joinColumns = @JoinColumn(name = "id_estudiante"),
            inverseJoinColumns = @JoinColumn(name = "id_asignatura")
    )
    private List<Asignatura> asignaturas;
}