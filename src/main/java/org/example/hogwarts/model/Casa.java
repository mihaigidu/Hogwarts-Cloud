package org.example.hogwarts.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
@Entity
public class Casa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCasa;

    private String nombreCasa;
    private String fundador;
    private String fantasma;

    // Añade esto para que coincida con la base de datos
    private boolean deleted = false;

    @OneToOne
    @JoinColumn(name = "id_jefe") // id_jefe en SQL mapea a jefeCasa en Java
    private Profesor jefeCasa;

    @OneToMany(mappedBy = "casa")
    private List<Estudiante> estudiantes;
}