package org.example.hogwarts.service;

import org.example.hogwarts.model.Estudiante;
import org.example.hogwarts.repository.EstudianteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class) // Paso 1: Extensión de Mockito
public class EstudianteServiceTest {

    @Mock // Paso 2: Dependencia simulada
    private EstudianteRepository estudianteRepository;

    @InjectMocks // Paso 2: Instancia real del servicio
    private EstudianteService estudianteService;

    private Estudiante harry;

    @BeforeEach // Paso 3: Preparar los datos
    void setUp() {
        harry = new Estudiante();
        harry.setIdEstudiante(1L);
        harry.setNombre("Harry");
        harry.setApellido("Potter");
    }

    @Test // Paso 4: Definir el caso de prueba
    void testExpulsarHarryPotter() {
        // GIVEN: Escenario (Precondiciones)
        // Programamos el comportamiento del mock (Paso 5)
        when(estudianteRepository.existsById(1L)).thenReturn(true);

        // WHEN: Acción (Llamada al método a probar)
        estudianteService.borrarEstudiante(1L);

        // THEN: Resultado (Verificaciones)
        // Verificamos que el borrado se llamó exactamente 1 vez (Paso 7 y requisito PDF)
        verify(estudianteRepository, times(1)).deleteById(1L);

        // Verificamos que se comprobó la existencia antes de borrar
        verify(estudianteRepository, times(1)).existsById(1L);
    }
}