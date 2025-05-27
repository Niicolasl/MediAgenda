package com.usco.agendamedicacomunitaria.repository;

import com.usco.agendamedicacomunitaria.model.Cita;
import com.usco.agendamedicacomunitaria.model.Paciente;
import com.usco.agendamedicacomunitaria.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {

    List<Cita> findByPaciente(Paciente paciente);

    List<Cita> findByProveedor(Proveedor proveedor);

    List<Cita> findByProveedorAndFechaHoraBetween(Proveedor proveedor, LocalDateTime inicio, LocalDateTime fin);

    List<Cita> findByPacienteAndFechaHoraBetween(Paciente paciente, LocalDateTime inicio, LocalDateTime fin);

    Optional<Cita> findByIdAndPaciente(Long id, Paciente paciente);

    Optional<Cita> findByIdAndProveedor(Long id, Proveedor proveedor);

    List<Cita> findByPacienteId(Long pacienteId);

    List<Cita> findByProveedorId(Long proveedorId);
    
    @Query("SELECT c FROM Cita c WHERE c.proveedor = :proveedor AND c.estado = :estado AND c.fechaHora > :fechaLimite")
    List<Cita> findByProveedorAndEstadoAndFechaHoraAfter(
        @Param("proveedor") Proveedor proveedor, 
        @Param("estado") Cita.EstadoCita estado, 
        @Param("fechaLimite") LocalDateTime fechaLimite
    );
}