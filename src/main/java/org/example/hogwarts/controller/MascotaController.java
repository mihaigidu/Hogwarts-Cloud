package org.example.hogwarts.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.hogwarts.dto.MascotaDTO;
import org.example.hogwarts.mapper.HogwartsMapper;
import org.example.hogwarts.model.Mascota;
import org.example.hogwarts.service.MascotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/mascotas")
@Tag(name = "Mascotas", description = "Registro de lechuzas, gatos, ratas y sapos de los estudiantes")
public class MascotaController {

    @Autowired
    private MascotaService mascotaService;

    @Autowired
    private HogwartsMapper mapper;

    @GetMapping
    @Operation(summary = "Listar mascotas", description = "Devuelve todas las mascotas registradas")
    @ApiResponse(responseCode = "200", description = "Lista obtenida")
    public ResponseEntity<List<MascotaDTO>> obtenerTodos() {
        List<Mascota> mascotas = mascotaService.obtenerTodos();
        List<MascotaDTO> dtos = mascotas.stream()
                .map(mapper::toMascotaDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar mascota", description = "Obtiene información de una mascota por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Mascota encontrada"),
            @ApiResponse(responseCode = "404", description = "Mascota no encontrada")
    })
    public ResponseEntity<MascotaDTO> obtenerPorId(@PathVariable Long id) {
        Optional<Mascota> mascotaOpt = mascotaService.obtenerPorId(id);

        return mascotaOpt
                .map(mascota -> ResponseEntity.ok(mapper.toMascotaDTO(mascota)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}