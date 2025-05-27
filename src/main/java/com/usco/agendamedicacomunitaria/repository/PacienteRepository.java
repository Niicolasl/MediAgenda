package com.usco.agendamedicacomunitaria.repository;

import com.usco.agendamedicacomunitaria.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Optional<Paciente> findByNumeroDocumento(String numeroDocumento);
    Optional<Paciente> findByEmail(String email);
}
