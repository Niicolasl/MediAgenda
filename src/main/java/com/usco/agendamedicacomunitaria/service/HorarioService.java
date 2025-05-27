package com.usco.agendamedicacomunitaria.service;

import com.usco.agendamedicacomunitaria.model.Horario;
import com.usco.agendamedicacomunitaria.model.Proveedor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface HorarioService {

    Horario crearHorario(Horario horario, Long proveedorId);

    List<Horario> listarHorariosPorProveedor(Long proveedorId);

    Optional<Horario> obtenerHorarioPorId(Long id);

    boolean eliminarHorario(Long id, Long proveedorId);

    Horario actualizarHorario(Long id, Horario horario, Long proveedorId);

    List<Horario> obtenerHorariosDisponiblesPorProveedor(Long proveedorId, LocalDateTime fechaInicio, LocalDateTime fechaFin);

}