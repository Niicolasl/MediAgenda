package com.usco.agendamedicacomunitaria.service;

import com.usco.agendamedicacomunitaria.model.Especialidad;
import com.usco.agendamedicacomunitaria.repository.EspecialidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EspecialidadServiceImpl implements EspecialidadService {

    private final EspecialidadRepository especialidadRepository;

    @Autowired
    public EspecialidadServiceImpl(EspecialidadRepository especialidadRepository) {
        this.especialidadRepository = especialidadRepository;
    }

    @Transactional
    @Override
    public Especialidad crearEspecialidad(Especialidad especialidad) {
        if (especialidadRepository.existsByNombre(especialidad.getNombre())) {
            throw new RuntimeException("Ya existe una especialidad con el nombre: " + especialidad.getNombre());
        }
        return especialidadRepository.save(especialidad);
    }

    @Override
    public Optional<Especialidad> obtenerEspecialidadPorId(Long id) {
        return especialidadRepository.findById(id);
    }

    @Override
    public Optional<Especialidad> obtenerEspecialidadPorNombre(String nombre) {
        return especialidadRepository.findByNombre(nombre);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Especialidad> listarTodasEspecialidades() {
        return especialidadRepository.findAll();
    }

    @Transactional
    @Override
    public Especialidad actualizarEspecialidad(Long id, Especialidad especialidadActualizada) {
        Optional<Especialidad> especialidadExistenteOptional = especialidadRepository.findById(id);
        if (especialidadExistenteOptional.isPresent()) {
            Especialidad especialidadExistente = especialidadExistenteOptional.get();
            if (!especialidadExistente.getNombre().equals(especialidadActualizada.getNombre()) && especialidadRepository.existsByNombre(especialidadActualizada.getNombre())) {
                throw new RuntimeException("Ya existe una especialidad con el nombre: " + especialidadActualizada.getNombre());
            }
            especialidadExistente.setNombre(especialidadActualizada.getNombre());
            return especialidadRepository.save(especialidadExistente);
        } else {
            throw new RuntimeException("Especialidad no encontrada con ID: " + id);
        }
    }

    @Transactional
    @Override
    public boolean eliminarEspecialidad(Long id) {
        if (especialidadRepository.existsById(id)) {
            especialidadRepository.deleteById(id);
            return true;
        }
        return false;
    }
}