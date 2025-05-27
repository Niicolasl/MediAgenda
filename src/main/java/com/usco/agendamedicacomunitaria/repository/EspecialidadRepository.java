package com.usco.agendamedicacomunitaria.repository;

import com.usco.agendamedicacomunitaria.model.Especialidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EspecialidadRepository extends JpaRepository<Especialidad, Long> {

    Optional<Especialidad> findByNombre(String nombre);

    boolean existsByNombre(String nombre);

    @Override
    @Query("SELECT e FROM Especialidad e JOIN FETCH e.proveedores")
    List<Especialidad> findAll();
}