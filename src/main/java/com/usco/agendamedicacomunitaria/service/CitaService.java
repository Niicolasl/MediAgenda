package com.usco.agendamedicacomunitaria.service;

import com.usco.agendamedicacomunitaria.model.Cita;
import com.usco.agendamedicacomunitaria.model.FranjaHorariaDisponible;
import com.usco.agendamedicacomunitaria.model.Paciente;
import com.usco.agendamedicacomunitaria.model.Proveedor;
import java.util.List;
import java.util.Optional;

public interface CitaService {

    Cita crearCita(Cita cita, Long pacienteId, Long proveedorId);

    Optional<Cita> findById(Long citaId);

    List<Cita> findByPacienteId(Long pacienteId);

    List<Cita> findByProveedorId(Long proveedorId);
    
    List<Cita> findPendientesByProveedorId(Long proveedorId);

    void cancelarCita(Long citaId);

    Cita actualizarEstadoCita(Long citaId, Cita.EstadoCita nuevoEstado);
}