package org.example.hogwarts.dto;

import lombok.Data;
import java.time.LocalDate;

// EstudianteCreateDTO.java
@Data
public class EstudianteCreateDTO {
    private String nombre;
    private String apellido;
    private int anyoCurso;
    private LocalDate fechaNacimiento;
    private Long casaId;
    private MascotaCreateDTO mascota; // DTO simple con solo nombre y especie
}

