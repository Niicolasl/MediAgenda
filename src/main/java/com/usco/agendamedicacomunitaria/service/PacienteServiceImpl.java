package com.usco.agendamedicacomunitaria.service;

import com.usco.agendamedicacomunitaria.model.Paciente;
import com.usco.agendamedicacomunitaria.model.Proveedor;
import com.usco.agendamedicacomunitaria.model.Usuario;
import com.usco.agendamedicacomunitaria.repository.CitaRepository;
import com.usco.agendamedicacomunitaria.repository.PacienteRepository;
import com.usco.agendamedicacomunitaria.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository pacienteRepository;
    private final UsuarioRepository usuarioRepository;
    private final CitaRepository citaRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PacienteServiceImpl(PacienteRepository pacienteRepository, 
                             UsuarioRepository usuarioRepository, 
                             CitaRepository citaRepository,
                             PasswordEncoder passwordEncoder) {
        this.pacienteRepository = pacienteRepository;
        this.usuarioRepository = usuarioRepository;
        this.citaRepository = citaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public Usuario registrarPaciente(Paciente paciente) {
        if (usuarioRepository.findByEmail(paciente.getEmail()).isPresent()) { // Usamos email como identificador único
            throw new RuntimeException("Email ya registrado");
        }
        if (pacienteRepository.findByNumeroDocumento(paciente.getNumeroDocumento()).isPresent()) {
            throw new RuntimeException("Número de documento ya registrado");
        }
        paciente.setPassword(passwordEncoder.encode(paciente.getPassword()));
        paciente.setFechaRegistro(LocalDateTime.now());
        paciente.setRol(Usuario.Rol.PACIENTE); // Establecemos el rol
        return pacienteRepository.save(paciente);
    }

    @Override
    public Optional<Paciente> obtenerPacientePorId(Long pacienteId) {
        return pacienteRepository.findById(pacienteId);
    }

    @Override
    public List<Paciente> listarTodosPacientes() {
        return pacienteRepository.findAll();
    }

    @Transactional
    @Override
    public Paciente actualizarPaciente(Long pacienteId, Paciente pacienteActualizado) {
        Optional<Paciente> pacienteExistenteOptional = pacienteRepository.findById(pacienteId);
        if (pacienteExistenteOptional.isPresent()) {
            Paciente pacienteExistente = pacienteExistenteOptional.get();
            // Permitir actualizar campos básicos incluyendo apellido (heredado de Usuario)
            pacienteExistente.setTipoDocumento(pacienteActualizado.getTipoDocumento());
            pacienteExistente.setNumeroDocumento(pacienteActualizado.getNumeroDocumento());
            pacienteExistente.setTelefono(pacienteActualizado.getTelefono());
            pacienteExistente.setGenero(pacienteActualizado.getGenero());
            pacienteExistente.setNombre(pacienteActualizado.getNombre());
            pacienteExistente.setApellido(pacienteActualizado.getApellido()); // Usamos el atributo heredado
            pacienteExistente.setEmail(pacienteActualizado.getEmail());
            // No permitimos actualizar la contraseña desde aquí por seguridad
            return pacienteRepository.save(pacienteExistente);
        } else {
            throw new RuntimeException("Paciente no encontrado con ID: " + pacienteId);
        }
    }

    @Transactional
    @Override
    public boolean eliminarPaciente(Long pacienteId) {
        if (pacienteRepository.existsById(pacienteId)) {
            pacienteRepository.deleteById(pacienteId);
            return true;
        }
        return false;
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<Proveedor> obtenerProveedoresPorPaciente(Long pacienteId) {
        // Verificar si el paciente existe
        if (!pacienteRepository.existsById(pacienteId)) {
            throw new NoSuchElementException("No se encontró el paciente con ID: " + pacienteId);
        }
        
        // Obtener todas las citas del paciente y extraer proveedores únicos
        return citaRepository.findByPacienteId(pacienteId).stream()
            .map(cita -> cita.getProveedor())
            .filter(Objects::nonNull)
            .distinct()
            .collect(Collectors.toList());
    }
}
