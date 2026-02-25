package org.example.hogwarts.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.hogwarts.model.Casa;
import org.example.hogwarts.repository.CasaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CasaService {

    @Autowired
    private CasaRepository casaRepository;

    public List<Casa> obtenerTodos() {
        return casaRepository.findAll();
    }

    public Optional<Casa> obtenerPorId(Long id) {
        return casaRepository.findById(id);
    }

    @Transactional
    public void eliminar(Long id) {
        // 1. Buscamos la casa
        Casa casa = casaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Casa no encontrada"));

        // 2. SOLO marcamos como borrado.
        // NO quites el jefe (setJefeCasa(null)), porque la base de datos no lo permite.
        casa.setDeleted(true);

        // 3. Guardamos el estado
        casaRepository.save(casa);
    }




}