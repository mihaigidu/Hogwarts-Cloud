package org.example.hogwarts.dto;

import lombok.Data;
import java.util.List;

@Data
public class CasaDTO {
    private Long id;
    private String nombre;
    private String fundador;
    private String fantasma;
    private ProfesorDTO jefe; // DTO anidado
    private List<String> estudiantes; // Solo lista de nombres
}