package org.example.hogwarts.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class EstudianteUpdateDTO {
    private int anyoCurso;
    private LocalDate fechaNacimiento;
    private MascotaCreateDTO mascota;
}