package org.example.hogwarts.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.hogwarts.dto.CasaDTO;
import org.example.hogwarts.mapper.HogwartsMapper;
import org.example.hogwarts.model.Casa;
import org.example.hogwarts.service.CasaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/casas")
@Tag(name = "Casas", description = "Gestión de las 4 casas de Hogwarts (Gryffindor, Slytherin, etc.)")
public class CasaController {

    @Autowired
    private CasaService casaService;

    @Autowired
    private HogwartsMapper mapper;

    @GetMapping
    @Operation(summary = "Listar casas", description = "Muestra las casas registradas en el sistema")
    @ApiResponse(responseCode = "200", description = "Éxito")
    public ResponseEntity<List<CasaDTO>> obtenerTodos() {
        List<Casa> casas = casaService.obtenerTodos();
        List<CasaDTO> dtos = casas.stream()
                .map(mapper::toCasaDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar casa", description = "Busca una casa por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Casa encontrada"),
            @ApiResponse(responseCode = "404", description = "Casa no encontrada")
    })
    public ResponseEntity<CasaDTO> obtenerPorId(@PathVariable Long id) {
        Optional<Casa> casaOpt = casaService.obtenerPorId(id);

        return casaOpt
                .map(casa -> ResponseEntity.ok(mapper.toCasaDTO(casa)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Disolver casa", description = "Elimina una casa del registro")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Casa eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró la casa a eliminar")
    })
    public ResponseEntity<Void> eliminarCasa(@PathVariable Long id) {
        if (casaService.obtenerPorId(id).isPresent()) {
            casaService.eliminar(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.notFound().build(); // 404 Not Found
    }
}