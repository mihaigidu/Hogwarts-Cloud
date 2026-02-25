package org.example.hogwarts.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.hogwarts.dto.ProfesorDTO;
import org.example.hogwarts.mapper.HogwartsMapper;
import org.example.hogwarts.model.Profesor;
import org.example.hogwarts.service.ProfesorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/profesores")
@Tag(name = "Profesores", description = "Gestión del personal docente de Hogwarts")
public class ProfesorController {

    @Autowired
    private ProfesorService profesorService;

    @Autowired
    private HogwartsMapper mapper;

    @GetMapping
    @Operation(summary = "Listar profesores", description = "Muestra el claustro de profesores")
    @ApiResponse(responseCode = "200", description = "Éxito")
    public ResponseEntity<List<ProfesorDTO>> obtenerTodos() {
        List<Profesor> profesores = profesorService.obtenerTodos();
        List<ProfesorDTO> dtos = profesores.stream()
                .map(mapper::toProfesorDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar profesor", description = "Busca un profesor específico por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Profesor encontrado"),
            @ApiResponse(responseCode = "404", description = "Profesor no encontrado")
    })
    public ResponseEntity<ProfesorDTO> obtenerPorId(@PathVariable Long id) {
        Optional<Profesor> profesorOpt = profesorService.obtenerPorId(id);

        return profesorOpt
                .map(profesor -> ResponseEntity.ok(mapper.toProfesorDTO(profesor)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}