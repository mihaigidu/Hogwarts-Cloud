package org.example.hogwarts.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.example.hogwarts.dto.EstudianteCreateDTO;
import org.example.hogwarts.dto.EstudianteDTO;
import org.example.hogwarts.dto.EstudianteUpdateDTO;
import org.example.hogwarts.mapper.HogwartsMapper;
import org.example.hogwarts.model.Estudiante;
import org.example.hogwarts.service.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/estudiantes")
@Tag(name = "Estudiantes", description = "API para la gestión de alumnos de Hogwarts") // Título bonito en Swagger
public class EstudianteController {

    @Autowired
    private EstudianteService estudianteService;

    @Autowired
    private HogwartsMapper mapper;

    // --- OBTENER TODOS ---
    @GetMapping
    @Operation(summary = "Obtener todos los estudiantes", description = "Devuelve una lista completa de los estudiantes registrados en Hogwarts")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    public ResponseEntity<List<EstudianteDTO>> obtenerTodos() {
        List<Estudiante> estudiantes = estudianteService.obtenerTodos();
        List<EstudianteDTO> dtos = estudiantes.stream()
                .map(mapper::toEstudianteDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // --- OBTENER POR ID ---
    @GetMapping("/{id}")
    @Operation(summary = "Buscar estudiante por ID", description = "Busca un estudiante específico usando su identificador único")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estudiante encontrado"),
            @ApiResponse(responseCode = "404", description = "Estudiante no encontrado en la base de datos")
    })
    public ResponseEntity<EstudianteDTO> obtenerPorId(@PathVariable Long id) {
        return estudianteService.obtenerPorId(id)
                .map(e -> ResponseEntity.ok(mapper.toEstudianteDTO(e)))
                .orElse(ResponseEntity.notFound().build());
    }

    // --- CREAR ---
    @PostMapping
    @Operation(summary = "Crear nuevo estudiante", description = "Registra un nuevo alumno en Hogwarts. Valida que los datos sean correctos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Estudiante creado con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos (faltan campos obligatorios)")
    })
    public ResponseEntity<EstudianteDTO> crearEstudiante(@RequestBody EstudianteCreateDTO createDTO) {
        // Validaciones de requisitos
        if (createDTO.getNombre() == null || createDTO.getApellido() == null ||
                createDTO.getAnyoCurso() <= 0 || createDTO.getFechaNacimiento() == null) {
            return ResponseEntity.badRequest().build();
        }

        try {
            Estudiante nuevo = estudianteService.crearEstudiante(createDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toEstudianteDTO(nuevo));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // --- ACTUALIZAR ---
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar estudiante", description = "Modifica los datos de un estudiante existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estudiante actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Estudiante no encontrado")
    })
    public ResponseEntity<EstudianteDTO> actualizarEstudiante(@PathVariable Long id, @RequestBody EstudianteUpdateDTO updateDTO) {
        // REQUISITO: Si falta algún campo (anyo o fecha), 400 Bad Request
        if (updateDTO.getAnyoCurso() <= 0 || updateDTO.getFechaNacimiento() == null) {
            return ResponseEntity.badRequest().build();
        }

        try {
            Estudiante actualizado = estudianteService.actualizarEstudiante(id, updateDTO);
            return ResponseEntity.ok(mapper.toEstudianteDTO(actualizado));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // --- BORRAR ---
    @DeleteMapping("/{id}")
    @Operation(summary = "Borrar estudiante", description = "Elimina un estudiante del registro de Hogwarts.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Estudiante eliminado correctamente (Sin contenido)"),
            @ApiResponse(responseCode = "404", description = "No se encontró el estudiante a borrar")
    })
    public ResponseEntity<Void> borrarEstudiante(@PathVariable Long id) {
        try {
            estudianteService.borrarEstudiante(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}