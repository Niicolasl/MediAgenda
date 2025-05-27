package com.usco.agendamedicacomunitaria.service;

import com.usco.agendamedicacomunitaria.model.Horario;
import com.usco.agendamedicacomunitaria.model.Proveedor;
import com.usco.agendamedicacomunitaria.repository.HorarioRepository;
import com.usco.agendamedicacomunitaria.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class HorarioServiceImpl implements HorarioService {

    private final HorarioRepository horarioRepository;
    private final ProveedorRepository proveedorRepository;

    @Autowired
    public HorarioServiceImpl(HorarioRepository horarioRepository, ProveedorRepository proveedorRepository) {
        this.horarioRepository = horarioRepository;
        this.proveedorRepository = proveedorRepository;
    }

    private void validarHorarioPerteneceProveedor(Long horarioId, Long proveedorId) {
        Optional<Horario> horarioOptional = horarioRepository.findById(horarioId);
        if (horarioOptional.isEmpty() || !horarioOptional.get().getProveedor().getId().equals(proveedorId)) {
            throw new RuntimeException("Horario no encontrado con ID: " + horarioId + " para el Proveedor con ID: " + proveedorId);
        }
    }

    @Override
    public Horario crearHorario(Horario horario, Long proveedorId) {
        return proveedorRepository.findById(proveedorId)
                .map(proveedor -> {
                    horario.setProveedor(proveedor);
                    return horarioRepository.save(horario);
                })
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con ID: " + proveedorId));
    }

    @Override
    public List<Horario> listarHorariosPorProveedor(Long proveedorId) {
        return proveedorRepository.findById(proveedorId)
                .map(horarioRepository::findByProveedor)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con ID: " + proveedorId));
    }

    @Override
    public Optional<Horario> obtenerHorarioPorId(Long id) {
        return horarioRepository.findById(id);
    }

    @Override
    public boolean eliminarHorario(Long id, Long proveedorId) {
        validarHorarioPerteneceProveedor(id, proveedorId);
        horarioRepository.deleteById(id);
        return true;
    }

    @Override
    public Horario actualizarHorario(Long id, Horario horarioActualizado, Long proveedorId) {
        validarHorarioPerteneceProveedor(id, proveedorId);
        return horarioRepository.findById(id)
                .map(horarioExistente -> {
                    horarioExistente.setFechaHoraInicio(horarioActualizado.getFechaHoraInicio());
                    horarioExistente.setFechaHoraFin(horarioActualizado.getFechaHoraFin());
                    horarioExistente.setDisponible(horarioActualizado.isDisponible());
                    return horarioRepository.save(horarioExistente);
                })
                .orElseThrow(() -> new RuntimeException("Horario no encontrado con ID: " + id));
    }

    @Override
    public List<Horario> obtenerHorariosDisponiblesPorProveedor(Long proveedorId, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return proveedorRepository.findById(proveedorId)
                .map(proveedor -> horarioRepository.findByProveedorAndFechaHoraInicioGreaterThanEqualAndFechaHoraFinLessThanEqualAndDisponibleTrue(
                        proveedor, fechaInicio, fechaFin
                ))
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con ID: " + proveedorId));
    }
}