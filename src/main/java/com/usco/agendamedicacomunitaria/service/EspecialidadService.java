package com.usco.agendamedicacomunitaria.service;

import com.usco.agendamedicacomunitaria.model.Especialidad;
import java.util.List;
import java.util.Optional;

public interface EspecialidadService {

    Especialidad crearEspecialidad(Especialidad especialidad);

    Optional<Especialidad> obtenerEspecialidadPorId(Long id);

    Optional<Especialidad> obtenerEspecialidadPorNombre(String nombre);

    List<Especialidad> listarTodasEspecialidades();

    Especialidad actualizarEspecialidad(Long id, Especialidad especialidadActualizada);

    boolean eliminarEspecialidad(Long id);
}