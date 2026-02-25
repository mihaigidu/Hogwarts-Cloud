package org.example.hogwarts.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.hogwarts.dto.EstudianteCreateDTO;
import org.example.hogwarts.dto.EstudianteUpdateDTO;
import org.example.hogwarts.model.Casa;
import org.example.hogwarts.model.Estudiante;
import org.example.hogwarts.model.Mascota;
import org.example.hogwarts.repository.CasaRepository;
import org.example.hogwarts.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EstudianteService {

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private CasaRepository casaRepository;

    public List<Estudiante> obtenerTodos() {
        return estudianteRepository.findAll();
    }

    public Optional<Estudiante> obtenerPorId(Long id) {
        return estudianteRepository.findById(id);
    }

    @Transactional
    public Estudiante crearEstudiante(EstudianteCreateDTO dto) {
        Estudiante estudiante = new Estudiante();
        estudiante.setNombre(dto.getNombre());
        estudiante.setApellido(dto.getApellido());
        estudiante.setAnyoCurso(dto.getAnyoCurso());
        estudiante.setFechaNacimiento(dto.getFechaNacimiento());

        // Asignación de Casa
        if (dto.getCasaId() != null) {
            Casa casa = casaRepository.findById(dto.getCasaId())
                    .orElseThrow(() -> new EntityNotFoundException("Casa no encontrada"));
            estudiante.setCasa(casa);
        }

        // Asignación de Mascota
        if (dto.getMascota() != null) {
            Mascota mascota = new Mascota();
            mascota.setNombre(dto.getMascota().getNombre());
            mascota.setEspecie(dto.getMascota().getEspecie());
            mascota.setEstudiante(estudiante); // Establecer relación bidireccional
            estudiante.setMascota(mascota);
        }

        return estudianteRepository.save(estudiante);
    }

    @Transactional
    public Estudiante actualizarEstudiante(Long id, EstudianteUpdateDTO dto) {
        Estudiante estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estudiante no encontrado"));

        // Actualización de campos básicos
        estudiante.setAnyoCurso(dto.getAnyoCurso());
        estudiante.setFechaNacimiento(dto.getFechaNacimiento());

        // Lógica de Mascota (REQUISITO: Si es null, eliminar. Si existe, actualizar/reemplazar)
        if (dto.getMascota() != null) {
            if (estudiante.getMascota() == null) {
                // Crear nueva si no tenía
                Mascota nuevaMascota = new Mascota();
                nuevaMascota.setNombre(dto.getMascota().getNombre());
                nuevaMascota.setEspecie(dto.getMascota().getEspecie());
                nuevaMascota.setEstudiante(estudiante);
                estudiante.setMascota(nuevaMascota);
            } else {
                // Actualizar la existente
                estudiante.getMascota().setNombre(dto.getMascota().getNombre());
                estudiante.getMascota().setEspecie(dto.getMascota().getEspecie());
            }
        } else {
            // REQUISITO: Si el DTO trae mascota null, se elimina la relación
            // Esto requiere orphanRemoval = true en la entidad Estudiante
            estudiante.setMascota(null);
        }

        return estudianteRepository.save(estudiante);
    }

    @Transactional
    public void borrarEstudiante(Long id) {
        if (!estudianteRepository.existsById(id)) {
            throw new EntityNotFoundException("Estudiante no encontrado");
        }
        estudianteRepository.deleteById(id);
    }
}