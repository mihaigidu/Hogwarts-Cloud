package org.example.hogwarts.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class EstudianteDTO {
    private Long id;
    private String nombre; // Nombre completo
    private int anyoCurso;
    private LocalDate fechaNacimiento;
    private String casa; // Nombre de la casa
    private MascotaDTO mascota;
    private List<AsignaturaCalificacionDTO> asignaturas; // Lista con notas
}