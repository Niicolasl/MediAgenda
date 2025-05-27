package com.usco.agendamedicacomunitaria.service;

import com.usco.agendamedicacomunitaria.model.Cita;
import com.usco.agendamedicacomunitaria.model.FranjaHorariaDisponible;
import com.usco.agendamedicacomunitaria.model.Paciente;
import com.usco.agendamedicacomunitaria.model.Proveedor;
import com.usco.agendamedicacomunitaria.repository.CitaRepository;
import com.usco.agendamedicacomunitaria.repository.FranjaHorariaDisponibleRepository;
import com.usco.agendamedicacomunitaria.repository.PacienteRepository;
import com.usco.agendamedicacomunitaria.repository.ProveedorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CitaServiceImpl implements CitaService {

    private final CitaRepository citaRepository;
    private final PacienteRepository pacienteRepository;
    private final ProveedorRepository proveedorRepository;
    private final FranjaHorariaDisponibleRepository franjaHorariaDisponibleRepository;

    @Autowired
    public CitaServiceImpl(CitaRepository citaRepository, PacienteRepository pacienteRepository,
                           ProveedorRepository proveedorRepository, FranjaHorariaDisponibleRepository franjaHorariaDisponibleRepository) {
        this.citaRepository = citaRepository;
        this.pacienteRepository = pacienteRepository;
        this.proveedorRepository = proveedorRepository;
        this.franjaHorariaDisponibleRepository = franjaHorariaDisponibleRepository;
    }

    @Transactional
    @Override
    public Cita crearCita(Cita cita, Long pacienteId, Long proveedorId) {
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con ID: " + pacienteId));

        Proveedor proveedor = proveedorRepository.findById(proveedorId)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con ID: " + proveedorId));

        LocalDateTime fechaHoraCita = cita.getFechaHora();

        // Buscar la franja horaria disponible para la hora exacta
        Optional<FranjaHorariaDisponible> franjaOptional =
                franjaHorariaDisponibleRepository.findDisponibleByProveedorYHora(
                        proveedor, fechaHoraCita
                );

        if (franjaOptional.isEmpty()) {
            throw new RuntimeException("No hay franja horaria disponible para la hora solicitada o ya fue reservada.");
        }

        FranjaHorariaDisponible franja = franjaOptional.get();
        
        // Verificar que la franja sigue disponible (doble verificación)
        if (!franja.isDisponible()) {
            throw new RuntimeException("La franja horaria ya no está disponible.");
        }
        
        // Marcar como no disponible y guardar
        franja.setDisponible(false);
        franjaHorariaDisponibleRepository.save(franja);

        cita.setPaciente(paciente);
        cita.setProveedor(proveedor);
        cita.setEstado(Cita.EstadoCita.PENDIENTE);

        return citaRepository.save(cita);
    }

    @Override
    public Optional<Cita> findById(Long citaId) {
        return citaRepository.findById(citaId);
    }

    @Override
    public List<Cita> findByPacienteId(Long pacienteId){
        return citaRepository.findByPacienteId(pacienteId);
    }

    @Override
    public List<Cita> findByProveedorId(Long proveedorId) {
        return citaRepository.findByProveedorId(proveedorId);
    }
    
    @Override
    public List<Cita> findPendientesByProveedorId(Long proveedorId) {
        Proveedor proveedor = proveedorRepository.findById(proveedorId)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con ID: " + proveedorId));
                
        LocalDateTime now = LocalDateTime.now();
        
        // Obtener citas pendientes que son futuras o recientes (últimas 24 horas)
        LocalDateTime fechaLimite = now.minusHours(24);
        
        return citaRepository.findByProveedorAndEstadoAndFechaHoraAfter(
            proveedor, 
            Cita.EstadoCita.PENDIENTE,
            fechaLimite
        );
    }

    @Transactional
    @Override
    public void cancelarCita(Long citaId) {
        Optional<Cita> citaOptional = citaRepository.findById(citaId);
        citaOptional.ifPresent(cita -> {
            cita.setEstado(Cita.EstadoCita.CANCELADA);
            citaRepository.save(cita);
            liberarFranjaHoraria(cita.getProveedor().getId(), cita.getFechaHora());
        });
    }

    private void liberarFranjaHoraria(Long proveedorId, LocalDateTime fechaHora) {
        proveedorRepository.findById(proveedorId)
                .ifPresent(proveedor -> {
                    Optional<FranjaHorariaDisponible> franjaOptional =
                            franjaHorariaDisponibleRepository.findDisponibleByProveedorYHora(
                                    proveedor, fechaHora
                            );
                    franjaOptional.ifPresent(franja -> {
                        if (!franja.isDisponible()) {
                            franja.setDisponible(true);
                            franjaHorariaDisponibleRepository.save(franja);
                        }
                    });
                });
    }
    
    @Transactional
    @Override
    public Cita actualizarEstadoCita(Long citaId, Cita.EstadoCita nuevoEstado) {
        Cita cita = citaRepository.findById(citaId)
                .orElseThrow(() -> new RuntimeException("No se encontró la cita con ID: " + citaId));
                
        // Si se está cancelando una cita, liberar la franja horaria
        if (nuevoEstado == Cita.EstadoCita.CANCELADA && 
            cita.getEstado() != Cita.EstadoCita.CANCELADA) {
            liberarFranjaHoraria(cita.getProveedor().getId(), cita.getFechaHora());
        }
        
        cita.setEstado(nuevoEstado);
        return citaRepository.save(cita);
    }
}
