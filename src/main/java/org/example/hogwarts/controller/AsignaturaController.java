package org.example.hogwarts.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.hogwarts.dto.AsignaturaDTO;
import org.example.hogwarts.mapper.HogwartsMapper;
import org.example.hogwarts.model.Asignatura;
import org.example.hogwarts.service.AsignaturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/asignaturas")
@Tag(name = "Asignaturas", description = "Gestión de las materias impartidas en Hogwarts")
public class AsignaturaController {

    @Autowired
    private AsignaturaService asignaturaService;

    @Autowired
    private HogwartsMapper mapper;

    @GetMapping
    @Operation(summary = "Listar asignaturas", description = "Devuelve todas las asignaturas disponibles (Pociones, Defensa contra las artes oscuras, etc.)")
    @ApiResponse(responseCode = "200", description = "Lista obtenida con éxito")
    public ResponseEntity<List<AsignaturaDTO>> obtenerTodos() {
        List<Asignatura> asignaturas = asignaturaService.obtenerTodos();
        List<AsignaturaDTO> dtos = asignaturas.stream()
                .map(mapper::toAsignaturaDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar asignatura", description = "Obtiene los detalles de una asignatura por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Asignatura encontrada"),
            @ApiResponse(responseCode = "404", description = "Asignatura no encontrada")
    })
    public ResponseEntity<AsignaturaDTO> obtenerPorId(@PathVariable Long id) {
        Optional<Asignatura> asignaturaOpt = asignaturaService.obtenerPorId(id);

        return asignaturaOpt
                .map(asignatura -> ResponseEntity.ok(mapper.toAsignaturaDTO(asignatura)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}