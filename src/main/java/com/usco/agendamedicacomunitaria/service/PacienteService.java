package com.usco.agendamedicacomunitaria.service;

import com.usco.agendamedicacomunitaria.model.Paciente;
import com.usco.agendamedicacomunitaria.model.Proveedor;
import com.usco.agendamedicacomunitaria.model.Usuario;
import java.util.List;
import java.util.Optional;

public interface PacienteService {

    Usuario registrarPaciente(Paciente paciente);

    Optional<Paciente> obtenerPacientePorId(Long pacienteId);

    List<Paciente> listarTodosPacientes();

    Paciente actualizarPaciente(Long pacienteId, Paciente pacienteActualizado);

    boolean eliminarPaciente(Long pacienteId);
    
    /**
     * Obtiene todos los proveedores con los que un paciente tiene citas programadas
     * @param pacienteId ID del paciente
     * @return Lista de proveedores únicos con los que el paciente tiene citas
     * @throws NoSuchElementException si no se encuentra el paciente
     */
    List<Proveedor> obtenerProveedoresPorPaciente(Long pacienteId);
}