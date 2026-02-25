package org.example.hogwarts.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ProfesorDTO {
    private Long id;
    private String nombre; // Nombre completo (Nombre + Apellido)
    private String asignatura; // Nombre de la asignatura que imparte
    private LocalDate fechaInicio;
}