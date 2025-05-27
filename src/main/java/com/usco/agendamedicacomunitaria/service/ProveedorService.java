package com.usco.agendamedicacomunitaria.service;

import com.usco.agendamedicacomunitaria.model.FranjaHorariaDisponible;
import com.usco.agendamedicacomunitaria.model.Horario;
import com.usco.agendamedicacomunitaria.model.Proveedor;
import com.usco.agendamedicacomunitaria.model.Usuario;
import com.usco.agendamedicacomunitaria.model.Especialidad;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProveedorService {

    Usuario registrarProveedor(Proveedor proveedor);

    Horario crearHorario(Horario horario, Long proveedorId);
    List<Horario> listarHorariosPorProveedor(Long proveedorId);
    Optional<Horario> obtenerHorarioPorId(Long horarioId);
    boolean eliminarHorario(Long horarioId, Long proveedorId);
    Horario actualizarHorario(Long horarioId, Horario horarioActualizado, Long proveedorId);
    List<Horario> obtenerHorariosDisponiblesPorProveedor(Long proveedorId, LocalDateTime fechaInicio, LocalDateTime fechaFin);

    Optional<Proveedor> obtenerProveedorPorId(Long proveedorId);
    List<Proveedor> listarTodosProveedores();
    Proveedor actualizarProveedor(Long proveedorId, Proveedor proveedorActualizado);
    boolean eliminarProveedor(Long proveedorId);

    Proveedor agregarEspecialidadProveedor(Long proveedorId, Long especialidadId);
    Proveedor eliminarEspecialidadProveedor(Long proveedorId, Long especialidadId);
    Set<Especialidad> listarEspecialidadesProveedor(Long proveedorId);

    List<Proveedor> buscarProveedoresPorEspecialidad(String especialidadNombre);

    List<FranjaHorariaDisponible> obtenerFranjasHorariasDisponiblesPorProveedorYFecha(Long proveedorId, LocalDateTime fecha);
    
    /**
     * Obtiene todos los pacientes que tienen citas con un proveedor específico
     * @param proveedorId ID del proveedor
     * @return Lista de pacientes únicos que tienen citas con el proveedor
     */
    List<Usuario> obtenerPacientesPorProveedor(Long proveedorId);
}