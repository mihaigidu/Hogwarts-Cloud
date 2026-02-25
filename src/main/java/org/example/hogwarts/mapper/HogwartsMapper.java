package org.example.hogwarts.mapper;

import org.example.hogwarts.dto.*;
import org.example.hogwarts.model.*;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class HogwartsMapper {

    public MascotaDTO toMascotaDTO(Mascota mascota) {
        if (mascota == null) return null;
        MascotaDTO dto = new MascotaDTO();
        dto.setId(mascota.getIdMascota());
        // En Mascota definiste: private String nombre; -> Getter: getNombre()
        dto.setNombre(mascota.getNombre());
        dto.setEspecie(mascota.getEspecie());
        if (mascota.getEstudiante() != null) {
            dto.setEstudiante(mascota.getEstudiante().getNombre() + " " + mascota.getEstudiante().getApellido());
        }
        return dto;
    }

    public ProfesorDTO toProfesorDTO(Profesor profesor) {
        if (profesor == null) return null;
        ProfesorDTO dto = new ProfesorDTO();
        dto.setId(profesor.getIdProfesor());
        dto.setNombre(profesor.getNombre() + " " + profesor.getApellido());
        dto.setFechaInicio(profesor.getFechaInicio());
        if (profesor.getAsignatura() != null) {
            // En Asignatura definiste: private String nombre; -> Getter: getNombre()
            dto.setAsignatura(profesor.getAsignatura().getNombre());
        }
        return dto;
    }

    public AsignaturaDTO toAsignaturaDTO(Asignatura asignatura) {
        if (asignatura == null) return null;
        AsignaturaDTO dto = new AsignaturaDTO();
        dto.setId(asignatura.getIdAsignatura());
        // En Asignatura definiste: private String nombre; -> Getter: getNombre()
        dto.setNombre(asignatura.getNombre());
        dto.setAula(asignatura.getAula());
        dto.setObligatoria(asignatura.isObligatoria());
        if (asignatura.getProfesor() != null) {
            dto.setProfesor(asignatura.getProfesor().getNombre() + " " + asignatura.getProfesor().getApellido());
        }
        return dto;
    }

    public EstudianteDTO toEstudianteDTO(Estudiante estudiante) {
        if (estudiante == null) return null;
        EstudianteDTO dto = new EstudianteDTO();
        dto.setId(estudiante.getIdEstudiante());
        dto.setNombre(estudiante.getNombre() + " " + estudiante.getApellido());
        dto.setAnyoCurso(estudiante.getAnyoCurso());
        dto.setFechaNacimiento(estudiante.getFechaNacimiento());

        // En Estudiante definiste: private Casa casa; -> Getter: getCasa()
        if (estudiante.getCasa() != null) {
            // Y en Casa definiste: private String nombreCasa; -> Getter: getNombreCasa()
            dto.setCasa(estudiante.getCasa().getNombreCasa());
        }

        dto.setMascota(toMascotaDTO(estudiante.getMascota()));

        if (estudiante.getAsignaturas() != null) {
            dto.setAsignaturas(estudiante.getAsignaturas().stream().map(asignatura -> {
                AsignaturaCalificacionDTO acDto = new AsignaturaCalificacionDTO();
                acDto.setAsignatura(asignatura.getNombre());
                acDto.setCalificacion(null); // Las notas están en la tabla intermedia, no en la entidad directa
                return acDto;
            }).collect(Collectors.toList()));
        }
        return dto;
    }

    public CasaDTO toCasaDTO(Casa casa) {
        if (casa == null) return null;
        CasaDTO dto = new CasaDTO();
        dto.setId(casa.getIdCasa());
        // En Casa definiste: private String nombreCasa; -> Getter: getNombreCasa()
        dto.setNombre(casa.getNombreCasa());
        dto.setFundador(casa.getFundador());
        dto.setFantasma(casa.getFantasma());

        dto.setJefe(toProfesorDTO(casa.getJefeCasa()));

        if (casa.getEstudiantes() != null) {
            dto.setEstudiantes(casa.getEstudiantes().stream()
                    .map(e -> e.getNombre() + " " + e.getApellido())
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    public Estudiante toEntity(EstudianteCreateDTO dto, Casa casa) {
        if (dto == null) return null;
        Estudiante e = new Estudiante();
        e.setNombre(dto.getNombre());
        e.setApellido(dto.getApellido());
        e.setAnyoCurso(dto.getAnyoCurso());
        e.setFechaNacimiento(dto.getFechaNacimiento());
        e.setCasa(casa);

        if (dto.getMascota() != null) {
            Mascota m = new Mascota();
            m.setNombre(dto.getMascota().getNombre());
            m.setEspecie(dto.getMascota().getEspecie());
            m.setEstudiante(e);
            e.setMascota(m);
        }
        return e;
    }
}