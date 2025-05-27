package com.usco.agendamedicacomunitaria.repository;

import com.usco.agendamedicacomunitaria.model.FranjaHorariaDisponible;
import com.usco.agendamedicacomunitaria.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FranjaHorariaDisponibleRepository extends JpaRepository<FranjaHorariaDisponible, Long> {

    @Deprecated
    List<FranjaHorariaDisponible> findByProveedorAndFechaOrderByHoraInicio(Proveedor proveedor, LocalDateTime fecha);

    @Query("SELECT f FROM FranjaHorariaDisponible f " +
            "WHERE f.proveedor = :proveedor " +
            "AND f.fecha = :fecha " +
            "AND f.horaInicio = :horaInicio " +
            "AND f.disponible = true")
    @Deprecated
    Optional<FranjaHorariaDisponible> findDisponibleByProveedorFechaHoraInicio(
            @Param("proveedor") Proveedor proveedor,
            @Param("fecha") LocalDateTime fecha,
            @Param("horaInicio") LocalDateTime horaInicio
    );

    @Query("SELECT f FROM FranjaHorariaDisponible f " +
            "WHERE f.proveedor = :proveedor " +
            "AND CAST(f.horaInicio AS date) = CAST(:fechaHora AS date) " +
            "AND HOUR(f.horaInicio) = HOUR(:fechaHora) " +
            "AND MINUTE(f.horaInicio) = MINUTE(:fechaHora) " +
            "AND f.disponible = true")
    Optional<FranjaHorariaDisponible> findDisponibleByProveedorYHora(
            @Param("proveedor") Proveedor proveedor,
            @Param("fechaHora") LocalDateTime fechaHora
    );
    
    @Query("SELECT f FROM FranjaHorariaDisponible f " +
            "WHERE f.proveedor = :proveedor " +
            "AND CAST(f.horaInicio AS date) = CAST(:fecha AS date) " +
            "AND f.disponible = true " +
            "AND NOT EXISTS (" +
            "   SELECT 1 FROM Cita c " +
            "   WHERE c.proveedor = f.proveedor " +
            "   AND c.fechaHora >= f.horaInicio " +
            "   AND c.fechaHora < f.horaFin " +
            "   AND (c.estado <> 'CANCELADA' AND c.estado <> 'RECHAZADA')" +
            ") " +
            "ORDER BY f.horaInicio")
    List<FranjaHorariaDisponible> findDisponiblesByProveedorYFecha(
            @Param("proveedor") Proveedor proveedor,
            @Param("fecha") LocalDateTime fecha
    );
    
    // Método alternativo para compatibilidad
    @Query("SELECT f FROM FranjaHorariaDisponible f " +
            "WHERE f.proveedor = :proveedor " +
            "AND CAST(f.horaInicio AS date) = CAST(:fecha AS date) " +
            "AND f.disponible = true " +
            "ORDER BY f.horaInicio")
    List<FranjaHorariaDisponible> findDisponiblesByProveedorYFechaSinExcluirCitas(
            @Param("proveedor") Proveedor proveedor,
            @Param("fecha") LocalDateTime fecha
    );
}
