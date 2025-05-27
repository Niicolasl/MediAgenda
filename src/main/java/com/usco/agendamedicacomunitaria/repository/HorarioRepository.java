package com.usco.agendamedicacomunitaria.repository;

import com.usco.agendamedicacomunitaria.model.Horario;
import com.usco.agendamedicacomunitaria.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Long> {

    List<Horario> findByProveedor(Proveedor proveedor);

    List<Horario> findByProveedorAndFechaHoraInicioGreaterThanEqualAndFechaHoraFinLessThanEqual(
            Proveedor proveedor, LocalDateTime inicio, LocalDateTime fin);

    List<Horario> findByProveedorAndFechaHoraInicioGreaterThanEqualAndDisponibleTrue(
            Proveedor proveedor, LocalDateTime ahora);

    Optional<Horario> findByIdAndProveedor(Long id, Proveedor proveedor);

    List<Horario> findByProveedorAndFechaHoraInicioGreaterThanEqualAndFechaHoraFinLessThanEqualAndDisponibleTrue(
            Proveedor proveedor, LocalDateTime inicio, LocalDateTime fin);

    @Query("SELECT h FROM Horario h WHERE h.proveedor = :proveedor AND h.fechaHoraInicio <= :fechaHora AND h.fechaHoraFin > :fechaHora AND h.disponible = true")
    Optional<Horario> findHorarioDisponibleParaCita(Proveedor proveedor, LocalDateTime fechaHora);

    @Query("SELECT h FROM Horario h WHERE h.proveedor = :proveedor AND h.fechaHoraInicio <= :inicio AND h.fechaHoraFin >= :fin AND h.disponible = true")
    Optional<Horario> findHorarioCubriendoRango(Proveedor proveedor, LocalDateTime inicio, LocalDateTime fin);
}